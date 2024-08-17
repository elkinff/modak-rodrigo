package com.example.utils.seed

import com.example.models.NotificationKinds
import com.example.models.NotificationTypes
import com.example.models.RateLimitRules
import com.example.models.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object SeedData {
	fun seed() {
		transaction {
			Users.insert {
				it[email] = "john.doe@example.com"
			}

			Users.insert {
				it[email] = "jane.smith@example.com"
			}


			NotificationKinds.insert {
				it[id] = 1
				it[kind] = "email"
			}


			NotificationTypes.insert {
				it[id] = 1
				it[idKind] = 1
				it[name] = "status"
			}

			NotificationTypes.insert {
				it[id] = 2
				it[idKind] = 1
				it[name] = "news"
			}

			NotificationTypes.insert {
				it[id] = 3
				it[idKind] = 1
				it[name] = "marketing"
			}


			RateLimitRules.insert {
				it[typeId] = 1
				it[limit] = 2
				it[timeWindow] = 600000 // 1 minuto
			}

			RateLimitRules.insert {
				it[typeId] = 1
				it[limit] = 10
				it[timeWindow] = 86400000 // 1 dia
			}

			RateLimitRules.insert {
				it[typeId] = 2
				it[limit] = 1
				it[timeWindow] = 86400000 // 1 dia
			}

			RateLimitRules.insert {
				it[typeId] = 3
				it[limit] = 3
				it[timeWindow] = 3600000 // 1 hora
			}
		}
	}
}