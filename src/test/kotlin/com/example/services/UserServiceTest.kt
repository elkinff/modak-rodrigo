package com.example.services

import com.example.models.User
import com.example.repositories.UserRepository
import io.ktor.server.plugins.NotFoundException
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class UserServiceTest {

	private val userRepository: UserRepository = mockk()
	private val userService = UserService(userRepository)

	@After
	fun tearDown() {
		clearAllMocks()
	}

	@Test
	fun `getUserById returns user when found`() {
		val userId = 1
		val user = User(id = userId, email = "test@test.com")

		every { userRepository.getUserById(userId) } returns user

		val result = userService.getUserById(userId)

		assertEquals(user, result)
		verify { userRepository.getUserById(userId) }
	}

	@Test
	fun `getUserById throws NotFoundException when user not found`() {
		val userId = 1

		every { userRepository.getUserById(userId) } returns null

		try {
			userService.getUserById(userId)
			fail("Expected NotFoundException was not thrown")
		} catch (e: NotFoundException) {
			assertEquals("User with id: '$userId' not found", e.message)
		} catch (e: Exception) {
			fail("Unexpected exception type: ${e.javaClass.name}")
		}
	}
}
