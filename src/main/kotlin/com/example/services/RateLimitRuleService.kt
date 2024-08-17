package com.example.services

import com.example.models.RateLimitRule
import com.example.repositories.RateLimitRuleRepository
import com.example.utils.requests.RateLimitRuleRequest

class RateLimitRuleService(
	private val notificationTypeService: NotificationTypeService,
	private val rateLimitRuleRepository: RateLimitRuleRepository,
	private val rateLimitValidator: RateLimitValidator
) {

	fun getRateLimitRulesByNotificationType(notificationTypeId: Int): List<RateLimitRule> {
		return rateLimitRuleRepository.getRateLimitRulesByNotificationType(notificationTypeId)
	}

	fun getRateLimitRules(): List<RateLimitRule> = rateLimitRuleRepository.getRateLimitRules()

	fun addRateLimitRule(rateLimitRuleRequest: RateLimitRuleRequest) {
		val notificationType = notificationTypeService.getNotificationTypeByName(rateLimitRuleRequest.type)
		rateLimitRuleRepository.addRateLimitRule(
			RateLimitRule(
				typeId = notificationType.id,
				limit = rateLimitRuleRequest.limit,
				timeWindow = rateLimitRuleRequest.timeWindow
			)
		)
	}

	fun validateRateLimitRulesByUser(notificationTypeId: Int, userId: Int) {
		val rateLimitRules = getRateLimitRulesByNotificationType(notificationTypeId)
		rateLimitValidator.validateRateLimitRulesByUser(rateLimitRules, userId)
	}
}