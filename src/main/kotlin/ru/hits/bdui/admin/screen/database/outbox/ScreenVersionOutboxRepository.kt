package ru.hits.bdui.admin.screen.database.outbox

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import ru.hits.bdui.admin.screen.database.emerge.emerge
import ru.hits.bdui.admin.screen.database.entity.ScreenMetaEntity
import ru.hits.bdui.admin.screen.database.entity.ScreenVersionEntity
import ru.hits.bdui.admin.screen.database.repository.ScreenVersionJpaRepository
import ru.hits.bdui.domain.screen.ScreenFromDatabase
import ru.hits.bdui.outbox.entity.OutboxEventEntity
import ru.hits.bdui.outbox.event.ScreenOutboxEventType
import ru.hits.bdui.outbox.repository.OutboxEventJpaRepository
import java.time.Instant
import java.util.UUID

@Component
class ScreenVersionOutboxRepository(
    private val repository: ScreenVersionJpaRepository,
    private val outboxRepository: OutboxEventJpaRepository,
    private val objectMapper: ObjectMapper,
    private val entityManager: EntityManager
) {
    @Transactional
    fun update(screen: ScreenFromDatabase): ScreenVersionEntity {
        val managedMetaRef = entityManager.getReference(ScreenMetaEntity::class.java, screen.meta.id.value)
        val entity = ScreenVersionEntity.emerge(screen, managedMetaRef)

        val saveEvent = OutboxEventEntity(
            id = UUID.randomUUID(),
            createdAt = Instant.now(),
            eventType = ScreenOutboxEventType.ScreenUpdated.value,
            payload = objectMapper.writeValueAsString(ScreenOutboxEventPayload.emerge(screen))
        )

        outboxRepository.save(saveEvent)
        return repository.save(entity)
    }
}