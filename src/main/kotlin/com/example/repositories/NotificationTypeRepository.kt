package com.example.repositories

import com.example.models.NotificationType
import com.example.models.NotificationTypes
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class  NotificationTypeRepository {

	 fun getNotificationTypeByName(typeName: String): NotificationType? {
		return transaction {
			NotificationTypes.select { NotificationTypes.name eq typeName }.singleOrNull()?.let {
				NotificationType(
					id = it[NotificationTypes.id],
					idKind = it[NotificationTypes.idKind],
					name = it[NotificationTypes.name]
				)
			}
		}
	}

	fun getAllNotificationTypes(): List<NotificationType> {
		return transaction {
			NotificationTypes.selectAll().map {
				NotificationType(
					id = it[NotificationTypes.id],
					idKind = it[NotificationTypes.idKind],
					name = it[NotificationTypes.name]
				)
			}
		}
	}

	fun addNotificationType(notificationType: NotificationType) {
		transaction {
			NotificationTypes.insert {
				it[idKind] = notificationType.idKind
				it[name] = notificationType.name
			}
		}
	}
}