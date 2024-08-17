package com.example.controllers

import com.example.services.RateLimitRuleService
import com.example.utils.requests.RateLimitRuleRequest
import com.example.utils.responses.SuccessResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.rateLimitRulesRouting() {
	val rateLimitRuleService by inject<RateLimitRuleService>()

	//get and create rate limit rules
	routing {
		get("/rate-limit-rules") {
			call.respond(
				status = HttpStatusCode.OK,
				message = rateLimitRuleService.getRateLimitRules()
			)
		}

		post("/rate-limit-rules") {
			rateLimitRuleService.addRateLimitRule(call.receive<RateLimitRuleRequest>())
			call.respond(
				status = HttpStatusCode.Created,
				message = SuccessResponse(message = "Rate Limit created successfully!")
			)
		}
	}
}