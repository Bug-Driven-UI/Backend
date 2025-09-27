package ru.hits.bdui.common.models.raw

import java.time.Instant

data class ErrorContentRaw(
    val timestampMs: Long,
    val message: String,
) {
    companion object {
        fun emerge(message: String): ErrorContentRaw =
            ErrorContentRaw(
                timestampMs = Instant.now().toEpochMilli(),
                message = message,
            )
    }
}