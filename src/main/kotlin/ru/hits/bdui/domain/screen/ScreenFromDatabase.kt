package ru.hits.bdui.domain.screen

import ru.hits.bdui.domain.ScreenId

/**
 * Обертка экрана для ответа из базы данных
 *
 * @property id идентификатор экрана
 * @property screen экран
 * @property version версия экрана
 */
data class ScreenFromDatabase(
    val id: ScreenId,
    val screen: Screen,
    val version: ScreenVersion?,
) {
    companion object
}