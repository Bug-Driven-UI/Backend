package ru.hits.bdui.admin.templates.database.entity

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import ru.hits.bdui.common.models.admin.entity.components.ComponentEntity
import java.util.UUID

@Entity
@Table(name = "templates")
data class TemplateEntity(
    @Id
    val id: UUID,
    @Column(nullable = false, unique = true)
    val name: String,
    @Type(JsonBinaryType::class)
    @Column(name = "component_json", nullable = false, columnDefinition = "jsonb")
    val component: ComponentEntity,
    @Column(name = "created_at_timestamp_ms", nullable = false)
    val createdAtTimestampMs: Long,
    @Column(name = "last_modified_at_timestamp_ms")
    val lastModifiedAtTimestampMs: Long?,
) {
    companion object
}