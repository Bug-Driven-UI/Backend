package ru.hits.bdui.admin.commands.database.entity

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import ru.hits.bdui.domain.command.Command
import java.util.UUID

@Entity
@Table(name = "commands")
data class CommandEntity(
    @Id
    val id: UUID,
    @Column(name = "name", nullable = false, unique = true)
    val name: String,
    @Type(JsonBinaryType::class)
    @Column(name = "command_json", nullable = false, columnDefinition = "jsonb")
    val command: Command,
    @Column(name = "created_at_timestamp_ms", nullable = false)
    val createdAtTimestampMs: Long,
    @Column(name = "last_modified_at_timestamp_ms")
    val lastModifiedAtTimestampMs: Long?,
) {
    companion object
}