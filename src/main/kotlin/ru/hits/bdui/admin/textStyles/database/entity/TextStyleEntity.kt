package ru.hits.bdui.admin.textStyles.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import ru.hits.bdui.domain.screen.styles.text.TextDecoration
import java.util.UUID

/**
 * Сущность для текстового стиля
 */
@Entity
@Table(name = "text_styles")
data class TextStyleEntity(
    @Id
    val id: UUID,
    @Column(name = "token", nullable = false, unique = true)
    val token: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "decoration")
    val decoration: TextDecoration?,
    @Column(name = "weight")
    val weight: Int?,
    @Column(name = "size", nullable = false)
    val size: Int,
    @Column(name = "line_height", nullable = false)
    val lineHeight: Int,
) {
    companion object
}