package ru.hits.bdui.outbox.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "outbox_cursor")
data class OutboxCursorEntity(
    @Id val id: Int,
    @Column(name = "last_sent_at")
    val lastSentAt: Instant,
)