package ru.hits.bdui.admin.screen.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.hits.bdui.admin.screen.database.entity.ScreenMetaEntity
import java.util.Optional
import java.util.UUID

interface ScreenMetaJpaRepository : JpaRepository<ScreenMetaEntity, UUID> {
    fun findByName(name: String): Optional<ScreenMetaEntity>

    @Query(
        value = """
            SELECT * FROM screens s
            WHERE s.name LIKE '%' || :name || '%'
        """,
        nativeQuery = true
    )
    fun findAllLikeName(name: String): List<ScreenMetaEntity>

    fun existsByNameIgnoreCase(name: String): Boolean

    @Modifying
    @Query(
        """
        update ScreenMetaEntity s
           set s.currentPublishedVersionId = :version
         where s.id = :screenId
    """
    )
    fun setPublishedVersion(
        @Param("screenId") screenId: UUID,
        @Param("version") version: UUID
    ): Int
}