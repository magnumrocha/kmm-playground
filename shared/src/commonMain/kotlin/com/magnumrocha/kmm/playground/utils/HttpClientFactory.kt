package com.magnumrocha.kmm.playground.utils

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.DEFAULT
import kotlinx.serialization.json.Json

class HttpClientFactory(private val enableNetworkLogs: Boolean) {

    fun getInstance(): HttpClient  = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(createJson())
        }
        if (enableNetworkLogs) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }

    private fun createJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
    }
}
