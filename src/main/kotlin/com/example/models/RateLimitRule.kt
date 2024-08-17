package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class RateLimitRule(
	val id: Int = 0,
	val typeId: Int,
	val limit: Int,
	val timeWindow: Long
)

object RateLimitRules: Table() {
	val id = integer("id").autoIncrement()
	val typeId = integer("type_id").references(NotificationTypes.id)
	val limit = integer("limit")
	val timeWindow = long("time_window")
	override val primaryKey = PrimaryKey(id)
}