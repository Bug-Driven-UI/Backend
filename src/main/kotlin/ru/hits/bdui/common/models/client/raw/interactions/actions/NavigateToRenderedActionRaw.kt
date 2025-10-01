package ru.hits.bdui.common.models.client.raw.interactions.actions

/**
 * Действие, отвечающее за навигацию
 *
 * @property screenName название экрана, куда требуется перейти
 * @property screenNavigationParams параметры для получения экрана
 */
data class NavigateToRenderedActionRaw(
    val screenName: String,
    val screenNavigationParams: Map<String, String>
) : RenderedActionRaw {
    override val type: String = "navigateTo"
}
