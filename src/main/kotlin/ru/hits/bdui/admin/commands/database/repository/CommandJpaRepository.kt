package ru.hits.bdui.admin.commands.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.hits.bdui.admin.commands.database.entity.CommandEntity
import java.util.Optional
import java.util.UUID

interface CommandJpaRepository : JpaRepository<CommandEntity, UUID> {
    fun findByName(name: String): Optional<CommandEntity>

    @Query(
        value = """
            SELECT * FROM commands c
            WHERE c.name LIKE '%' || :name || '%'
        """,
        nativeQuery = true
    )
    fun findAllLikeName(name: String): List<CommandEntity>
}