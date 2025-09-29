package ru.hits.bdui.admin.colorStyles.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.hits.bdui.admin.colorStyles.database.entity.ColorStyleEntity
import java.util.Optional
import java.util.UUID

interface ColorStyleJpaRepository : JpaRepository<ColorStyleEntity, UUID> {
    fun findByToken(token: String): Optional<ColorStyleEntity>

    @Query(
        value = """
            SELECT * FROM color_styles cs
            WHERE cs.token LIKE '%' || :token || '%'
        """,
        nativeQuery = true
    )
    fun findAllLikeTokens(token: String): List<ColorStyleEntity>

    fun findAllByTokenIn(tokens: Set<String>): List<ColorStyleEntity>
}