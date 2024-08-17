package com.example.services

import com.example.models.NotificationType
import com.example.repositories.NotificationTypeRepository
import com.example.utils.requests.NotificationTypeRequest
import io.ktor.server.plugins.NotFoundException

class NotificationTypeService(
	private val notificationKindService: NotificationKindService,
	private val notificationTypeRepository: NotificationTypeRepository
) {

	fun getNotificationTypeByName(typeName: String): NotificationType {
		return notificationTypeRepository.getNotificationTypeByName(typeName)
			 ?: throw NotFoundException("Invalid notification type: $typeName")
	}

	fun getAllNotificationTypes(): List<NotificationType> = notificationTypeRepository.getAllNotificationTypes()

	fun addNotificationType(notificationTypeRequest: NotificationTypeRequest) {
		val notificationKindId = notificationKindService.getNotificationKindByName(notificationTypeRequest.kindName)
		notificationTypeRepository.addNotificationType(
			NotificationType(
				idKind = notificationKindId.id,
				name = notificationTypeRequest.name,
			)
		)
	}
}