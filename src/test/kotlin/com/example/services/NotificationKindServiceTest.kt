package com.example.services

import com.example.models.NotificationKind
import com.example.repositories.NotificationKindRepository
import io.ktor.server.plugins.NotFoundException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class NotificationKindServiceTest {

	private val notificationKindRepository: NotificationKindRepository = mockk()
	private val notificationKindService = NotificationKindService(notificationKindRepository)

	@Test
	fun `getNotificationKindByName returns notification kind when found`() {
		val kindName = "email"
		val expectedNotificationKind = NotificationKind(id = 1, kind = kindName)
		every { notificationKindRepository.getNotificationKindByName(kindName) } returns expectedNotificationKind

		val result = notificationKindService.getNotificationKindByName(kindName)

		assertEquals(expectedNotificationKind, result)
		verify { notificationKindRepository.getNotificationKindByName(kindName) }
	}

	@Test
	fun `getNotificationKindByName throws NotFoundException when not found`() {
		val kindName = "UNKNOWN"
		every { notificationKindRepository.getNotificationKindByName(kindName) } returns null

		val exception = assertThrows<NotFoundException> {
			notificationKindService.getNotificationKindByName(kindName)
		}
		assertEquals("Invalid notification kind: $kindName", exception.message)
		verify { notificationKindRepository.getNotificationKindByName(kindName) }
	}
}