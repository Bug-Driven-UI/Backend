package ru.hits.bdui.admin.templates.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import ru.hits.bdui.admin.templates.database.entity.serialization.ComponentAttributeConverter
import ru.hits.bdui.domain.screen.components.Component
import java.util.UUID

@Entity
@Table(name = "templates")
data class TemplateEntity(
    @Id
    val id: UUID,
    @Column(nullable = false, unique = true)
    val name: String,
    @Convert(converter = ComponentAttributeConverter::class)
    @Column(name = "component_json", nullable = false, columnDefinition = "jsonb")
    val component: Component,
    @Column(name = "created_at_timestamp_ms", nullable = false)
    val createdAtTimestampMs: Long,
    @Column(name = "last_modified_at_timestamp_ms")
    val lastModifiedAtTimestampMs: Long?,
) {
    companion object
}