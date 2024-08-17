package com.example.repositories

import com.example.models.Notification
import com.example.models.Notifications
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class  NotificationRepository {

	fun getNotificationsCountByTimeWindow(typeId: Int, userId: Int, timeWindow: Long): Long {
		return transaction {
			Notifications.select {
				(Notifications.typeId eq typeId) and
					(Notifications.userId eq userId) and
						(Notifications.timestamp greaterEq (timeWindow))
			}.count()
		}
	}

	fun addNotification(notification: Notification) {
		transaction {
			Notifications.insert {
				it[userId] = notification.userId
				it[typeId] = notification.typeId
				it[message] = notification.message
				it[timestamp] = notification.timestamp
				it[createdAt] = notification.createdAt
			}
		}
	}
}