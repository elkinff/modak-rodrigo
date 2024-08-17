package com.example.utils.exceptions

data class RateLimitExceededException(
	override val message: String
): RuntimeException(message)
