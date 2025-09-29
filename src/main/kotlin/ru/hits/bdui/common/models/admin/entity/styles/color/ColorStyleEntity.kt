package ru.hits.bdui.common.models.admin.entity.styles.color

/**
 * Стиль цвета
 *
 * @property token - токен цвета, хранящийся в бд
 */
data class ColorStyleEntity(
    val token: String,
    val color: String,
)