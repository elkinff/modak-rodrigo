package com.example.utils.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
	val error: String
)
