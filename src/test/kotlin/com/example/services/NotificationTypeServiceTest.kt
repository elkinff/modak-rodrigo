package com.example.services

import com.example.models.NotificationKind
import com.example.models.NotificationType
import com.example.repositories.NotificationTypeRepository
import com.example.utils.requests.NotificationTypeRequest
import io.ktor.server.plugins.NotFoundException
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class NotificationTypeServiceTest {

	private val notificationKindService: NotificationKindService = mockk()
	private val notificationTypeRepository: NotificationTypeRepository = mockk()
	private val notificationTypeService = NotificationTypeService(
		notificationKindService,
		notificationTypeRepository
	)

	@After
	fun tearDown() {
		clearAllMocks()
	}

	@Test
	fun `getNotificationTypeByName returns notification type when found`() {
		val typeName = "status"
		val notificationType = NotificationType(id = 1, idKind = 1, name = typeName)

		every { notificationTypeRepository.getNotificationTypeByName(typeName) } returns notificationType

		val result = notificationTypeService.getNotificationTypeByName(typeName)

		assertEquals(notificationType, result)
		verify { notificationTypeRepository.getNotificationTypeByName(typeName) }
	}

	@Test(expected = NotFoundException::class)
	fun `getNotificationTypeByName throws NotFoundException when not found`() {
		val typeName = "UNKNOWN"

		every { notificationTypeRepository.getNotificationTypeByName(typeName) } returns null

		notificationTypeService.getNotificationTypeByName(typeName)
	}

	@Test
	fun `getAllNotificationTypes returns all notification types`() {
		val notificationTypes = listOf(
			NotificationType(id = 1, idKind = 1, name = "status"),
			NotificationType(id = 2, idKind = 1, name = "marketing")
		)

		every { notificationTypeRepository.getAllNotificationTypes() } returns notificationTypes

		val result = notificationTypeService.getAllNotificationTypes()

		assertEquals(notificationTypes, result)
		verify { notificationTypeRepository.getAllNotificationTypes() }
	}

	@Test
	fun `addNotificationType adds notification type`() {
		val notificationTypeRequest = NotificationTypeRequest(name = "status", kindName = "email")
		val notificationKind = NotificationKind(id = 1, kind = "email")

		every { notificationKindService.getNotificationKindByName(notificationTypeRequest.kindName) } returns notificationKind
		every { notificationTypeRepository.addNotificationType(any()) } just Runs

		notificationTypeService.addNotificationType(notificationTypeRequest)

		verify {
			notificationKindService.getNotificationKindByName(notificationTypeRequest.kindName)
			notificationTypeRepository.addNotificationType(
				withArg {
					assertEquals(notificationKind.id, it.idKind)
					assertEquals(notificationTypeRequest.name, it.name)
				}
			)
		}
	}
}
