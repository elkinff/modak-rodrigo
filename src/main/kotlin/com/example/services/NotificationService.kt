package com.example.services

import com.example.models.Notification
import com.example.repositories.NotificationRepository
import com.example.utils.providers.RealTimeProvider
import com.example.utils.requests.NotificationRequest

class NotificationService (
	private val notificationTypeService: NotificationTypeService,
	private val userService: UserService,
	private val rateLimitRuleService: RateLimitRuleService,
	private val notificationRepository: NotificationRepository,
	private val timeProvider: RealTimeProvider
) {

	fun validateEmailNotification(notificationRequest: NotificationRequest) {
		val notificationType = notificationTypeService.getNotificationTypeByName(notificationRequest.type)
		val user = userService.getUserById(notificationRequest.userId)

		rateLimitRuleService.validateRateLimitRulesByUser(notificationType.id, user.id)

		sendNotification(user.id, notificationType.id, notificationRequest.message)
	}

	fun sendNotification(userId: Int, notificationTypeId: Int, message: String) {
		notificationRepository.addNotification(
			Notification(
				userId = userId,
				typeId = notificationTypeId,
				message = message,
				timestamp = timeProvider.currentTimeMillis(),
				createdAt = timeProvider.currentLocalDateTime().toString()
			)
		)
	}
}