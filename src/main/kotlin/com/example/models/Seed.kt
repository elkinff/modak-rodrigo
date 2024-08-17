package com.example.models

import org.jetbrains.exposed.dao.id.IntIdTable

object Seeds: IntIdTable() {
	val name = varchar("name", 255).uniqueIndex()
	val executedAt = long("executed_at")
}