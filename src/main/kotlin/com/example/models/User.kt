package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
	val id: Int,
	val email: String
)

object Users: Table() {
	val id = integer("id").autoIncrement()
	val email = varchar("email", 100)
	override val primaryKey = PrimaryKey(id)
}