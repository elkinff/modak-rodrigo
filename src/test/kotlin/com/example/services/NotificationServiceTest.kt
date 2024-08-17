import com.example.models.NotificationType
import com.example.models.User
import com.example.repositories.NotificationRepository
import com.example.services.NotificationService
import com.example.services.NotificationTypeService
import com.example.services.RateLimitRuleService
import com.example.services.UserService
import com.example.utils.providers.RealTimeProvider
import com.example.utils.requests.NotificationRequest
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class NotificationServiceTest {

	private val notificationTypeService: NotificationTypeService = mockk()
	private val userService: UserService = mockk()
	private val rateLimitRuleService: RateLimitRuleService = mockk()
	private val notificationRepository: NotificationRepository = mockk()
	private val timeProvider: RealTimeProvider = mockk()
	private val notificationService = NotificationService(
		notificationTypeService,
		userService,
		rateLimitRuleService,
		notificationRepository,
		timeProvider
	)

	private val fixedTimestamp = 1723848323700L
	private val fixedDateTime = LocalDateTime.parse("2024-08-16T17:45:23.700")

	@Before
	fun setUp() {
		every { timeProvider.currentTimeMillis() } returns fixedTimestamp
		every { timeProvider.currentLocalDateTime() } returns fixedDateTime
	}

	@After
	fun tearDown() {
		clearAllMocks()
	}

	@Test
	fun `validateEmailNotification calls services and sendNotification`() {
		val notificationRequest = NotificationRequest("status", 1, "Test message")
		val notificationType = NotificationType(id = 1, idKind = 1, name = "status")
		val user = User(id = 1, email = "test@test.com")

		every { notificationTypeService.getNotificationTypeByName(notificationRequest.type) } returns notificationType
		every { userService.getUserById(notificationRequest.userId) } returns user
		every { rateLimitRuleService.validateRateLimitRulesByUser(notificationType.id, user.id) } just Runs
		every { notificationRepository.addNotification(any()) } just Runs

		notificationService.validateEmailNotification(notificationRequest)

		verify { notificationTypeService.getNotificationTypeByName(notificationRequest.type) }
		verify { userService.getUserById(notificationRequest.userId) }
		verify { rateLimitRuleService.validateRateLimitRulesByUser(notificationType.id, user.id) }
		verify { notificationRepository.addNotification(
			withArg {
				assertEquals(user.id, it.userId)
				assertEquals(notificationType.id, it.typeId)
				assertEquals(notificationRequest.message, it.message)
				assertEquals(fixedTimestamp, it.timestamp)
				assertEquals(fixedDateTime.toString(), it.createdAt)
			}
		) }
	}

	@Test
	fun `sendNotification calls addNotification with correct parameters`() {
		val userId = 1
		val notificationTypeId = 1
		val message = "Test message"

		every { notificationRepository.addNotification(any()) } just Runs

		notificationService.sendNotification(userId, notificationTypeId, message)

		verify { notificationRepository.addNotification(
			withArg {
				assertEquals(userId, it.userId)
				assertEquals(notificationTypeId, it.typeId)
				assertEquals(message, it.message)
				assertEquals(fixedTimestamp, it.timestamp)
				assertEquals(fixedDateTime.toString(), it.createdAt)
			}
		) }
	}
}
