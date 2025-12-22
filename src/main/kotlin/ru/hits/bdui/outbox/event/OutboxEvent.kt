package ru.hits.bdui.outbox.event

import java.time.Instant
import java.util.UUID

data class OutboxEvent(
    val id: UUID,
    val payload: String,
    val createdAt: Instant,
    val type: OutboxEventType,
)

sealed interface OutboxEventType {
    val value: String

    companion object {
        // TODO(Подумать как сделать так, чтобы не пришлось самому сюда докидывать типы)
        private val valueToType: Map<String, OutboxEventType> =
            listOf(
                ScreenOutboxEventType.ScreenUpdated,
                ScreenOutboxEventType.ScreenCreated
            ).associateBy { it.value }

        fun fromString(value: String): OutboxEventType =
            valueToType[value] ?: throw IllegalArgumentException("Unknown outbox event type: $value")
    }
}