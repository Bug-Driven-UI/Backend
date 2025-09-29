package ru.hits.bdui.admin.textStyles.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.hits.bdui.admin.textStyles.database.entity.TextStyleEntity
import java.util.Optional
import java.util.UUID

interface TextStyleJpaRepository : JpaRepository<TextStyleEntity, UUID> {
    fun findByToken(token: String): Optional<TextStyleEntity>

    @Query(
        value = """
            SELECT * FROM text_styles ts
            WHERE ts.token LIKE '%' || :token || '%'
        """,
        nativeQuery = true
    )
    fun findAllLikeTokens(token: String): List<TextStyleEntity>

    fun findAllByTokenIn(tokens: Set<String>): List<TextStyleEntity>
}