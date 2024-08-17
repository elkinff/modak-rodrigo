package com.example

import com.example.config.DatabaseInitializer
import com.example.controllers.notificationTypesRouting
import com.example.controllers.notificationsRouting
import com.example.controllers.rateLimitRulesRouting
import com.example.services.NotificationTypeService
import com.example.services.UserService
import com.example.utils.handlers.configureExceptionHandling
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module
import com.example.repositories.*
import com.example.services.NotificationKindService
import com.example.services.NotificationService
import com.example.services.RateLimitRuleService
import com.example.services.RateLimitValidator
import com.example.utils.providers.RealTimeProvider
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

val koinModule = module {
    single { NotificationTypeService(get(), get()) }
    single { UserService(get()) }
    single { RateLimitValidator(get()) }
    single { RateLimitRuleService(get(), get(), get()) }
    single { NotificationService(get(), get(), get(), get(), get()) }
    single { NotificationKindService(get()) }
    single { RealTimeProvider()  }

    single { RateLimitRuleRepository() }
    single { NotificationRepository() }
    single { NotificationKindRepository() }
    single { NotificationTypeRepository() }
    single { UserRepository() }
}

fun Application.module() {
    DatabaseInitializer.initialize()

    install(Koin) {
        modules(koinModule)
    }

    install(ContentNegotiation) {
        json()
    }

    notificationsRouting()
    rateLimitRulesRouting()
    notificationTypesRouting()
    configureExceptionHandling()
}


