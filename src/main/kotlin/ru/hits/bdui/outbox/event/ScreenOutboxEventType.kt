package ru.hits.bdui.outbox.event

sealed interface ScreenOutboxEventType : OutboxEventType {
    override val value: String

    data object ScreenUpdated : ScreenOutboxEventType {
        override val value: String = "screen_updated"
    }

    data object ScreenCreated : ScreenOutboxEventType {
        override val value: String = "screen_created"
    }
}