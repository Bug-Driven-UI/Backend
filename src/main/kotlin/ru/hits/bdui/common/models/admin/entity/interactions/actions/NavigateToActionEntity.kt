package ru.hits.bdui.common.models.admin.entity.interactions.actions

/**
 * Действие, отвечающее за навигацию
 *
 * @property screenName название экрана, куда требуется перейти
 * @property screenNavigationParams параметры для получения экрана
 */
data class NavigateToActionEntity(
    val screenName: String,
    val screenNavigationParams: Map<String, String>
) : ActionEntity {
    override val type: String = "navigateTo"
}