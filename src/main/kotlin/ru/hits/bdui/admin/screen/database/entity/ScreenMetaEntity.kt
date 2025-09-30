package ru.hits.bdui.admin.screen.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.util.UUID

@Entity
@Table(name = "screens")
data class ScreenMetaEntity(
    @Id
    val id: UUID,
    @Column(name = "name", nullable = false, unique = true)
    val name: String,
    @Column(name = "description", nullable = false)
    val description: String,
    @Column(name = "current_published_version_id")
    var currentPublishedVersionId: UUID?,
) {
    companion object

    // Поле для оптимистической блокировки
    @Version
    var rowVersion: Long? = null
}