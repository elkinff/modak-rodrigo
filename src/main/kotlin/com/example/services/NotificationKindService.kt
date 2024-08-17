package com.example.services

import com.example.models.NotificationKind
import com.example.repositories.NotificationKindRepository
import io.ktor.server.plugins.NotFoundException

class NotificationKindService(
	private val notificationKindRepository: NotificationKindRepository
) {

	fun getNotificationKindByName(kindName: String): NotificationKind {
		return notificationKindRepository.getNotificationKindByName(kindName)
			?: throw NotFoundException("Invalid notification kind: $kindName")
	}
}