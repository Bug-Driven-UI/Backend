package ru.hits.bdui.client.action.models

/**
 * Действие, отвечающее за навигацию
 *
 * @property screenName название экрана, куда требуется перейти
 * @property screenNavigationParams параметры для получения экрана
 */
data class ExecutableNavigateToActionRaw(
    val screenName: String,
    val screenNavigationParams: Map<String, String>
) : ExecutableActionRaw {
    override val type: String = "navigateTo"
}
