package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Notification(
	val id: Int = 0,
	val userId: Int,
	val typeId: Int,
	val message: String,
	val timestamp: Long,
	val createdAt: String
)

object Notifications: Table() {
	private val id = integer("id").autoIncrement()
	val userId = integer("user_id").references(Users.id)
	val typeId = integer("type_id").references(NotificationTypes.id)
	val message = varchar("message", 255)
	val timestamp = long("timestamp")
	val createdAt = varchar("created_at", 100)
	override val primaryKey = PrimaryKey(id)
}