package ru.hits.bdui.admin.templates.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.hits.bdui.admin.templates.database.entity.TemplateEntity
import java.util.Optional
import java.util.UUID

interface ComponentTemplateJpaRepository : JpaRepository<TemplateEntity, UUID> {
    fun findByName(name: String): Optional<TemplateEntity>

    @Query(
        value = """
            SELECT * FROM templates t
            WHERE t.name LIKE '%' || :name || '%'
        """,
        nativeQuery = true
    )
    fun findAllLikeName(name: String): List<TemplateEntity>

    fun findAllByNameIn(names: Set<String>): List<TemplateEntity>
}