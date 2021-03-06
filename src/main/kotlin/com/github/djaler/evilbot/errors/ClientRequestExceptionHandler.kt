package com.github.djaler.evilbot.errors

import com.github.djaler.evilbot.clients.SentryClient
import io.ktor.client.call.*
import io.ktor.client.features.*
import org.springframework.stereotype.Component

@Component
class ClientRequestExceptionHandler(
    private val sentryClient: SentryClient
) : ExceptionHandler {
    override suspend fun handleException(e: Exception) {
        if (e !is ClientRequestException) {
            return
        }

        val response: String = e.response.receive()
        sentryClient.setExtra("response", response)
    }
}
