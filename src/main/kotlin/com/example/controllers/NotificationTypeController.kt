package com.example.controllers

import com.example.services.NotificationService
import com.example.services.NotificationTypeService
import com.example.utils.requests.NotificationTypeRequest
import com.example.utils.responses.SuccessResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.notificationTypesRouting() {
	val notificationTypeService by inject<NotificationTypeService>()

	//get and create notification types (status, marketing, etc)
	routing {
		get("/notification-types") {
			call.respond(
				status = HttpStatusCode.OK,
				message = notificationTypeService.getAllNotificationTypes()
			)
		}

		post("/notification-types") {
			notificationTypeService.addNotificationType(call.receive<NotificationTypeRequest>())
			call.respond(
				status = HttpStatusCode.Created,
				message = SuccessResponse(message = "Notification type created successfully!")
			)
		}
	}

}