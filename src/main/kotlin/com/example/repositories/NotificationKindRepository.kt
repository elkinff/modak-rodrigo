package com.example.repositories

import com.example.models.NotificationKind
import com.example.models.NotificationKinds
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class NotificationKindRepository {

	fun getNotificationKindByName(kindName: String): NotificationKind? {
		return transaction {
			NotificationKinds.select { NotificationKinds.kind eq kindName }.singleOrNull()?.let {
				NotificationKind(
					id = it[NotificationKinds.id],
					kind = it[NotificationKinds.kind],
				)
			}
		}
	}
}