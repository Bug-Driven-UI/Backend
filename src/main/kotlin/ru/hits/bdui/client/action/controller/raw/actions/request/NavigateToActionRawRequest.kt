package ru.hits.bdui.client.action.controller.raw.actions.request

/**
 * Действие, отвечающее за навигацию
 *
 * @property screenName название экрана, куда требуется перейти
 * @property screenNavigationParams параметры для получения экрана
 */
data class NavigateToActionRawRequest(
    val screenName: String,
    val screenNavigationParams: Map<String, String>
) : ActionRawRequest {
    override val type: String = "navigateTo"
}