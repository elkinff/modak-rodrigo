package com.example.utils.handlers

import com.example.utils.exceptions.RateLimitExceededException
import com.example.utils.responses.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureExceptionHandling() {
	install(StatusPages) {
		exception<NotFoundException> { call, cause ->
			call.respond(
				status = HttpStatusCode.NotFound,
				message = ErrorResponse(cause.message ?: "Not found")
			)
		}

		exception<RateLimitExceededException> { call, cause ->
			call.respond(
				status = HttpStatusCode.TooManyRequests,
				message = ErrorResponse(cause.message)
			)
		}

		exception<BadRequestException> { call, cause ->
			call.respond(
				status = HttpStatusCode.BadRequest,
				message = ErrorResponse("Incorrect request body")
			)
		}

		exception<Throwable> { call, cause ->
			call.respond(
				status = HttpStatusCode.InternalServerError,
				message = ErrorResponse(cause.message ?: "An unexpected error occurred")
			)
		}
	}
}