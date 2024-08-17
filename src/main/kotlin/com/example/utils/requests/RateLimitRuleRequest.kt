package com.example.utils.requests

import kotlinx.serialization.Serializable

@Serializable
data class RateLimitRuleRequest(
	val type: String,
	val limit: Int,
	val timeWindow: Long
)
