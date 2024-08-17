package com.example.utils.providers

import java.time.LocalDateTime

interface TimeProvider {
	fun currentTimeMillis(): Long
	fun currentLocalDateTime(): LocalDateTime
}

class RealTimeProvider : TimeProvider {
	override fun currentTimeMillis(): Long = System.currentTimeMillis()
	override fun currentLocalDateTime(): LocalDateTime = LocalDateTime.now()
}