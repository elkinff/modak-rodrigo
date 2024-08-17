package com.example.repositories

import com.example.models.RateLimitRule
import com.example.models.RateLimitRules
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class RateLimitRuleRepository {

	fun getRateLimitRulesByNotificationType(notificationTypeId: Int): List<RateLimitRule> {
		return transaction {
			RateLimitRules.select { RateLimitRules.typeId eq notificationTypeId }.map {
				RateLimitRule(
					id = it[RateLimitRules.id],
					typeId = it[RateLimitRules.typeId],
					limit = it[RateLimitRules.limit],
					timeWindow = it[RateLimitRules.timeWindow]
				)
			}
		}
	}

	fun getRateLimitRules(): List<RateLimitRule> {
		return transaction {
			RateLimitRules.selectAll().map {
				RateLimitRule(
					id = it[RateLimitRules.id],
					typeId = it[RateLimitRules.typeId],
					limit = it[RateLimitRules.limit],
					timeWindow = it[RateLimitRules.timeWindow]
				)
			}
		}
	}

	fun addRateLimitRule(rateLimitRule: RateLimitRule) {
		transaction {
			RateLimitRules.insert {
				it[typeId] = rateLimitRule.typeId
				it[limit] = rateLimitRule.limit
				it[timeWindow] = rateLimitRule.timeWindow
			}
		}
	}

}