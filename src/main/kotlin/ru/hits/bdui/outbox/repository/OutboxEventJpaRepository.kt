package ru.hits.bdui.outbox.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.hits.bdui.outbox.entity.OutboxEventEntity
import ru.hits.bdui.outbox.event.OutboxEvent
import ru.hits.bdui.outbox.event.OutboxEventType
import ru.hits.bdui.outbox.scheduler.config.OutboxProperties
import java.time.Instant
import java.util.UUID

interface OutboxEventJpaRepository : JpaRepository<OutboxEventEntity, UUID> {
    @Query(
        value = """
    SELECT * FROM outbox_events e
    WHERE e.created_at > :start
      AND e.created_at <= :finish
    ORDER BY e.created_at
    LIMIT :batchSize
  """,
        nativeQuery = true
    )
    fun findOutboxEventsInBatch(batchSize: Int, start: Instant, finish: Instant): List<OutboxEventEntity>
}

@Repository
class OutboxEventRepository(
    private val outboxEventJpaRepository: OutboxEventJpaRepository,
    private val outboxProperties: OutboxProperties
) {
    fun findOutboxEventsInBatch(
        start: Instant,
        finish: Instant,
    ): Map<OutboxEventType, List<OutboxEvent>> =
        outboxEventJpaRepository.findOutboxEventsInBatch(
            batchSize = outboxProperties.batchSize,
            start = start,
            finish = finish
        )
            .map {
                OutboxEvent(
                    id = it.id,
                    payload = it.payload,
                    type = OutboxEventType.fromString(it.eventType),
                    createdAt = it.createdAt,
                )
            }
            .groupBy { it.type }
}