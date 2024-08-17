package com.example.utils.requests

import kotlinx.serialization.Serializable

@Serializable
data class NotificationTypeRequest(
	val kindName: String,
	val name: String
)
