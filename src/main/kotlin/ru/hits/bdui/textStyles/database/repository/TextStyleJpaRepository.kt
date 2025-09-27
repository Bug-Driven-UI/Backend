package ru.hits.bdui.textStyles.database.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.hits.bdui.textStyles.database.entity.TextStyleEntity
import java.util.Optional
import java.util.UUID

interface TextStyleJpaRepository : JpaRepository<TextStyleEntity, UUID> {
    fun findByToken(token: String): Optional<TextStyleEntity>
}