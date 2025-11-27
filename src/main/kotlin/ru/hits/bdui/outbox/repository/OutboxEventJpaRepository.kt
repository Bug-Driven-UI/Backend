package ru.hits.bdui.outbox.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.hits.bdui.outbox.entity.OutboxEventEntity
import java.util.UUID

interface OutboxEventJpaRepository : JpaRepository<OutboxEventEntity, UUID>