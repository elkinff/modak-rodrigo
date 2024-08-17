package com.example.repositories

import com.example.models.User
import com.example.models.Users
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

	fun getUserById(id: Int): User? {
		return transaction {
			Users.select { Users.id eq id }.singleOrNull()?.let {
				User(
					id = it[Users.id],
					email = it[Users.email]
				)
			}
		}
	}
}