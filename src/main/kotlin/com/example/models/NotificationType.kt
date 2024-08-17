package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class NotificationType(
	val id: Int = 0,
	val idKind: Int,
	val name: String
)

object NotificationTypes: Table() {
	val id = integer("id").autoIncrement()
	val idKind = integer("id_kind").references(NotificationKinds.id)
	val name = varchar("name", 50)
	override val primaryKey = PrimaryKey(id)
}