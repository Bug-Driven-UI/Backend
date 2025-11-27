package ru.hits.bdui.outbox.screen

sealed interface ScreenOutboxEvent {
    val value: String

    data object ScreenUpdated : ScreenOutboxEvent {
        override val value: String = "screen_updated"
    }

    data object ScreenCreated : ScreenOutboxEvent {
        override val value: String = "screen_created"
    }
}