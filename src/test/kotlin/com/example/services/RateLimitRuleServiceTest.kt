package com.example.services

import com.example.models.NotificationType
import com.example.models.RateLimitRule
import com.example.repositories.RateLimitRuleRepository
import com.example.utils.requests.RateLimitRuleRequest
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test


class RateLimitRuleServiceTest {
	private val notificationTypeService: NotificationTypeService = mockk()
	private val rateLimitRuleRepository: RateLimitRuleRepository = mockk()
	private val rateLimitValidator: RateLimitValidator = mockk()
	private val rateLimitRuleService = RateLimitRuleService(
		notificationTypeService,
		rateLimitRuleRepository,
		rateLimitValidator
	)

	@After
	fun tearDown() {
		clearAllMocks()
	}

	@Test
	fun `getRateLimitRulesByNotificationType returns rate limit rules`() {
		val notificationTypeId = 1
		val rateLimitRules = listOf(
			RateLimitRule(typeId = notificationTypeId, limit = 100, timeWindow = 3600),
			RateLimitRule(typeId = notificationTypeId, limit = 200, timeWindow = 7200)
		)

		every { rateLimitRuleRepository.getRateLimitRulesByNotificationType(notificationTypeId) } returns rateLimitRules

		val result = rateLimitRuleService.getRateLimitRulesByNotificationType(notificationTypeId)

		assertEquals(rateLimitRules, result)
		verify { rateLimitRuleRepository.getRateLimitRulesByNotificationType(notificationTypeId) }
	}

	@Test
	fun `getRateLimitRules returns all rate limit rules`() {
		val rateLimitRules = listOf(
			RateLimitRule(typeId = 1, limit = 100, timeWindow = 3600),
			RateLimitRule(typeId = 2, limit = 200, timeWindow = 7200)
		)

		every { rateLimitRuleRepository.getRateLimitRules() } returns rateLimitRules

		val result = rateLimitRuleService.getRateLimitRules()

		assertEquals(rateLimitRules, result)
		verify { rateLimitRuleRepository.getRateLimitRules() }
	}

	@Test
	fun `addRateLimitRule adds rate limit rule`() {
		val rateLimitRuleRequest = RateLimitRuleRequest(type = "status", limit = 100, timeWindow = 3600)
		val notificationType = NotificationType(id = 1, idKind = 1, name = "status")

		every { notificationTypeService.getNotificationTypeByName(rateLimitRuleRequest.type) } returns notificationType
		every { rateLimitRuleRepository.addRateLimitRule(any()) } just Runs

		rateLimitRuleService.addRateLimitRule(rateLimitRuleRequest)

		verify {
			notificationTypeService.getNotificationTypeByName(rateLimitRuleRequest.type)
			rateLimitRuleRepository.addRateLimitRule(
				withArg {
					assertEquals(notificationType.id, it.typeId)
					assertEquals(rateLimitRuleRequest.limit, it.limit)
					assertEquals(rateLimitRuleRequest.timeWindow, it.timeWindow)
				}
			)
		}
	}

	@Test
	fun `validateRateLimitRulesByUser calls rateLimitValidator`() {
		val notificationTypeId = 1
		val userId = 1
		val rateLimitRules = listOf(
			RateLimitRule(typeId = notificationTypeId, limit = 100, timeWindow = 3600)
		)

		every { rateLimitRuleRepository.getRateLimitRulesByNotificationType(notificationTypeId) } returns rateLimitRules
		every { rateLimitValidator.validateRateLimitRulesByUser(rateLimitRules, userId) } just Runs

		rateLimitRuleService.validateRateLimitRulesByUser(notificationTypeId, userId)

		verify { rateLimitRuleRepository.getRateLimitRulesByNotificationType(notificationTypeId) }
		verify { rateLimitValidator.validateRateLimitRulesByUser(rateLimitRules, userId) }
	}
}
