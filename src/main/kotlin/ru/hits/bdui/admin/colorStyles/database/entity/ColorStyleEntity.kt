package ru.hits.bdui.admin.colorStyles.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

/**
 * Сущность для стиля цвета
 */
@Entity
@Table(name = "color_styles")
data class ColorStyleEntity(
    @Id
    val id: UUID,
    @Column(name = "token", nullable = false, unique = true)
    val token: String,
    @Column(name = "color", nullable = false)
    val color: String,
) {
    companion object
}