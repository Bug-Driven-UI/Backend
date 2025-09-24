package ru.hits.bdui.domain.screen.interactions.actions

import ru.hits.bdui.domain.ScreenName
import ru.hits.bdui.domain.ValueOrExpression

/**
 * Действие, отвечающее за обновление экрана
 *
 * @property screenName название экрана
 * @property screenNavigationParams параметры для обновления экрана
 */
data class UpdateScreenAction(
    val screenName: ScreenName,
    val screenNavigationParams: Map<String, ValueOrExpression>
) : Action {
    override val type: String = "updateScreen"
}