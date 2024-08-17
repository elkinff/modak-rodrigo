package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class NotificationKind(
	val id: Int = 0,
	val kind: String
)

object NotificationKinds: Table() {
	val id = integer("id").autoIncrement()
	val kind = varchar("kind", 20)
	override val primaryKey = PrimaryKey(id)
}