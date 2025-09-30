package ru.hits.bdui.admin.screen.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.hits.bdui.admin.screen.database.entity.ScreenVersionEntity
import java.util.Optional
import java.util.UUID

interface ScreenVersionJpaRepository : JpaRepository<ScreenVersionEntity, UUID> {
    @Query(
        value = """
            SELECT sv.*
            FROM screen_versions sv
            JOIN screens s ON sv.screen_id = s.id
            WHERE sv.id = :versionId
              AND s.id = :screenId
        """,
        nativeQuery = true
    )
    fun findByIdAndScreenId(screenId: UUID, versionId: UUID): Optional<ScreenVersionEntity>

    @Query(
        value = """
            SELECT sv.*
            FROM screen_versions sv
            WHERE sv.screen_id = :screenId
            ORDER BY sv.version DESC
        """,
        nativeQuery = true,
    )
    fun findAllVersionsByScreenId(screenId: UUID): List<ScreenVersionEntity>

    @Query(
        value = """
            SELECT sv.*
            FROM screen_versions sv
            WHERE sv.screen_id = :screenId
            ORDER BY sv.version DESC
            LIMIT 1
        """,
        nativeQuery = true,
    )
    fun findLatestScreenVersion(screenId: UUID): Optional<ScreenVersionEntity>
}