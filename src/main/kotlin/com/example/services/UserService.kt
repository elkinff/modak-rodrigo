package com.example.services

import com.example.models.User
import com.example.repositories.UserRepository
import io.ktor.server.plugins.NotFoundException

class UserService(
	private val userRepository: UserRepository
) {

	fun getUserById(userId: Int): User {
		return userRepository.getUserById(userId)
			?: throw NotFoundException("User with id: '${userId}' not found")
	}
}