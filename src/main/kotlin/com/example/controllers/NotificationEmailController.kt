package com.example.controllers

import com.example.services.NotificationService
import com.example.utils.requests.NotificationRequest
import com.example.utils.responses.SuccessResponse
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.HttpStatusCode
import org.koin.ktor.ext.inject

fun Application.notificationsRouting() {
    val notificationService by inject<NotificationService>()

    //Send and validate rate limit rules email notifications
    routing {
        post("/notifications/email") {
            notificationService.validateEmailNotification(call.receive<NotificationRequest>())

            call.respond(
                status = HttpStatusCode.Created,
                message = SuccessResponse(message = "Notification sent successfully!")
            )
        }
    }
}
