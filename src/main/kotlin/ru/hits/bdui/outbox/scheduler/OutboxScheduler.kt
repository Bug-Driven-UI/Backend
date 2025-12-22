package ru.hits.bdui.outbox.scheduler

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.hits.bdui.outbox.OutboxSender
import ru.hits.bdui.outbox.event.OutboxEventType
import ru.hits.bdui.outbox.repository.OutboxCursorJpaRepository
import ru.hits.bdui.outbox.repository.OutboxEventRepository
import ru.hits.bdui.outbox.scheduler.config.OutboxProperties
import java.time.Instant

@Component
class OutboxScheduler(
    private val properties: OutboxProperties,
    private val cursorRepository: OutboxCursorJpaRepository,
    private val eventRepository: OutboxEventRepository,
    senders: List<OutboxSender>,
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val typeToSender: Map<OutboxEventType, OutboxSender> = senders.associateBy { it.type }

    @Scheduled(fixedRate = 5000)
    fun outboxScreenEvents() {
        log.info("Начал вычитку outbox событий")
        val cursor = cursorRepository.findCursor()
        val finish = minOf(cursor.lastSentAt + properties.windowSize, Instant.now().minus(properties.lag))
        val typeToEvents = eventRepository.findOutboxEventsInBatch(
            start = cursor.lastSentAt,
            finish = finish,
        )

        if (typeToEvents.isEmpty()) {
            log.info("Отсутствуют события для обработки")
            cursorRepository.updateCursor(finish)
            return
        }

        log.info("Получено {} типов событий и {} событий", typeToEvents.keys.size, typeToEvents.values.flatten().size)

        typeToEvents.entries.forEach { entry ->
            val sender = typeToSender[entry.key]
            if (sender != null) {
                sender.send(entry.value)
            } else {
                log.error("Отсутствует отправитель событий для типа: {}", entry.key)
            }
        }

        val lastCreatedAt = typeToEvents.values
            .flatten()
            .maxOf { it.createdAt }
        cursorRepository.updateCursor(lastCreatedAt)
    }
}