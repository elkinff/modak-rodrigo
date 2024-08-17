package com.example.utils.requests

import kotlinx.serialization.Serializable

@Serializable
data class NotificationRequest(
	val type: String,
	val userId: Int,
	val message: String
)
