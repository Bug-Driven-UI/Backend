package ru.hits.bdui.admin.screen.database.entity

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.hibernate.annotations.Type
import ru.hits.bdui.common.models.admin.entity.screen.ScreenEntity
import java.util.UUID

@Entity
@Table(name = "screen_versions")
data class ScreenVersionEntity(
    @Id
    val id: UUID,
    @ManyToOne(optional = false, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "screen_id", nullable = false)
    val meta: ScreenMetaEntity,
    @Column(nullable = false)
    val version: Int,
    @Type(JsonBinaryType::class)
    @Column(name = "screen_json", nullable = false, columnDefinition = "jsonb")
    val screen: ScreenEntity,
    @Column(name = "created_at_timestamp_ms", nullable = false)
    val createdAtTimestampMs: Long,
    @Column(name = "last_modified_at_timestamp_ms")
    val lastModifiedAtTimestampMs: Long?,
) {
    companion object

    // Поле для оптимистической блокировки
    @Version
    var rowVersion: Long? = null
}