package com.example.services

import com.example.models.RateLimitRule
import com.example.repositories.NotificationRepository
import com.example.utils.exceptions.RateLimitExceededException

class RateLimitValidator(
	private val notificationRepository: NotificationRepository
) {

	fun validateRateLimitRulesByUser(rateLimitRules: List<RateLimitRule>, userId: Int) {
		rateLimitRules.forEach {
			val recentNotificationsCount = notificationRepository.getNotificationsCountByTimeWindow(
				it.typeId, userId, System.currentTimeMillis() - it.timeWindow
			)

			if (recentNotificationsCount >= it.limit) {
				throw RateLimitExceededException("Rate limit exceeded. Please try again later.")
			}
		}
	}
}