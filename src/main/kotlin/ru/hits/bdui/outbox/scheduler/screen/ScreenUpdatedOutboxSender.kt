package ru.hits.bdui.outbox.scheduler.screen

import org.springframework.stereotype.Component
import ru.hits.bdui.outbox.OutboxSender
import ru.hits.bdui.outbox.event.OutboxEvent
import ru.hits.bdui.outbox.event.OutboxEventType
import ru.hits.bdui.outbox.event.ScreenOutboxEventType
import ru.hits.bdui.outbox.scheduler.TransactionalKafkaEventSender
import ru.hits.bdui.outbox.scheduler.config.ScreenOutboxProperties

@Component
class ScreenUpdatedOutboxSender(
    private val kafkaSender: TransactionalKafkaEventSender,
    private val properties: ScreenOutboxProperties,
) : OutboxSender {
    override val type: OutboxEventType = ScreenOutboxEventType.ScreenUpdated

    override fun send(events: List<OutboxEvent>) {
        kafkaSender.send(
            topic = properties.updated.name,
            events = events,
        )
    }
}