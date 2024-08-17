package com.example.config

import com.example.models.NotificationKinds
import com.example.models.NotificationTypes
import com.example.models.Notifications
import com.example.models.RateLimitRules
import com.example.models.Seeds
import com.example.models.Users
import com.example.utils.seed.SeedData
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseInitializer {

	fun initialize() {
		Database.connect(url = "jdbc:sqlite:sample.db", driver = "org.sqlite.JDBC")

		transaction {
			SchemaUtils.create(Users, Notifications, RateLimitRules, NotificationTypes, NotificationKinds)
		}
		seed()
	}

	private fun seed() {
		transaction {
			SchemaUtils.create(Seeds)

			val isSeedExecuted = Seeds.select { Seeds.name eq "initial_seed" }.count() > 0

			if (!isSeedExecuted) {
				SeedData.seed()

				Seeds.insert {
					it[name] = "initial_seed"
					it[executedAt] = System.currentTimeMillis()
				}
			}
		}
	}
}