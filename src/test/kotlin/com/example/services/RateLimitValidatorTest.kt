package com.example.services

import com.example.models.RateLimitRule
import com.example.repositories.NotificationRepository
import com.example.utils.exceptions.RateLimitExceededException
import io.mockk.*
import org.junit.After
import org.junit.Test

class RateLimitValidatorTest {

	private val notificationRepository: NotificationRepository = mockk()
	private val rateLimitValidator = RateLimitValidator(notificationRepository)

	@After
	fun tearDown() {
		clearAllMocks()
	}

	@Test(expected = RateLimitExceededException::class)
	fun `validateRateLimitRulesByUser throws RateLimitExceededException when limit exceeded`() {
		val rateLimitRules = listOf(
			RateLimitRule(typeId = 1, limit = 5, timeWindow = 3600)
		)
		val userId = 1
		val recentNotificationsCount = 6L

		every { notificationRepository.getNotificationsCountByTimeWindow(any(), any(), any()) } returns recentNotificationsCount

		rateLimitValidator.validateRateLimitRulesByUser(rateLimitRules, userId)
	}

	@Test
	fun `validateRateLimitRulesByUser does not throw exception when limit not exceeded`() {
		val rateLimitRules = listOf(
			RateLimitRule(typeId = 1, limit = 5, timeWindow = 3600)
		)
		val userId = 1
		val recentNotificationsCount = 4L

		every { notificationRepository.getNotificationsCountByTimeWindow(any(), any(), any()) } returns recentNotificationsCount

		try {
			rateLimitValidator.validateRateLimitRulesByUser(rateLimitRules, userId)
		} catch (e: RateLimitExceededException) {
			throw AssertionError("Exception was thrown: ${e.message}")
		}
	}

	@Test
	fun `validateRateLimitRulesByUser checks all rate limit rules`() {
		val rateLimitRules = listOf(
			RateLimitRule(typeId = 1, limit = 5, timeWindow = 3600),
			RateLimitRule(typeId = 2, limit = 10, timeWindow = 7200)
		)
		val userId = 1

		every { notificationRepository.getNotificationsCountByTimeWindow(1, userId, any()) } returns 4
		every { notificationRepository.getNotificationsCountByTimeWindow(2, userId, any()) } returns 8

		try {
			rateLimitValidator.validateRateLimitRulesByUser(rateLimitRules, userId)
		} catch (e: RateLimitExceededException) {
			throw AssertionError("Exception was thrown: ${e.message}")
		}

		verify {
			notificationRepository.getNotificationsCountByTimeWindow(1, userId, any())
			notificationRepository.getNotificationsCountByTimeWindow(2, userId, any())
		}
	}
}