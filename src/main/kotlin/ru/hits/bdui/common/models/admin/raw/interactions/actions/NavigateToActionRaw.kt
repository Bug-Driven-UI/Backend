package ru.hits.bdui.common.models.admin.raw.interactions.actions

/**
 * Действие, отвечающее за навигацию
 *
 * @property screenName название экрана, куда требуется перейти
 * @property screenNavigationParams параметры для получения экрана
 */
data class NavigateToActionRaw(
    val screenName: String,
    val screenNavigationParams: Map<String, String>
) : ActionRaw {
    override val type: String = "navigateTo"
}