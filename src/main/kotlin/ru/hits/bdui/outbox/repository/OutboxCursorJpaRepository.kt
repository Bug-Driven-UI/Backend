package ru.hits.bdui.outbox.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.hits.bdui.outbox.entity.OutboxCursorEntity
import java.time.Instant

@Repository
interface OutboxCursorJpaRepository : JpaRepository<OutboxCursorEntity, Int> {
    /**
     * Возвращает сущность курсора.
     *
     * Сущность создана на уровне миграций
     */
    @Query(
        """
        SELECT * FROM outbox_cursor
        WHERE id = 1 FOR UPDATE
        """, nativeQuery = true
    )
    @Transactional
    fun findCursor(): OutboxCursorEntity

    @Modifying
    @Query(
        """
        UPDATE outbox_cursor
        SET last_sent_at = :lastSentAt
        WHERE id = 1
        """, nativeQuery = true
    )
    @Transactional
    fun updateCursor(lastSentAt: Instant)
}