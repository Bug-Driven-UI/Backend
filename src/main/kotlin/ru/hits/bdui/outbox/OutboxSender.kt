package ru.hits.bdui.outbox

import ru.hits.bdui.outbox.event.OutboxEvent
import ru.hits.bdui.outbox.event.OutboxEventType

interface OutboxSender {
    val type: OutboxEventType

    fun send(events: List<OutboxEvent>)
}