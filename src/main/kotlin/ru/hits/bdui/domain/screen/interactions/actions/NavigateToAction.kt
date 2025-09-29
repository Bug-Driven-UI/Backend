package ru.hits.bdui.domain.screen.interactions.actions

import ru.hits.bdui.domain.ScreenName
import ru.hits.bdui.domain.ValueOrExpression

/**
 * Действие, отвечающее за навигацию
 *
 * @property screenName название экрана, куда требуется перейти
 * @property screenNavigationParams параметры для получения экрана
 */
data class NavigateToAction(
    val screenName: ScreenName,
    val screenNavigationParams: Map<String, ValueOrExpression>
) : Action {
    override val type: String = "navigateTo"
}