package ru.hits.bdui.common.models.admin.raw.interactions.actions

/**
 * Действие, отвечающее за обновление экрана
 *
 * @property screenName название экрана
 * @property screenNavigationParams параметры для обновления экрана
 */
data class UpdateScreenActionRaw(
    val screenName: String,
    val screenNavigationParams: Map<String, String>
) : ActionRaw {
    override val type: String = "updateScreen"
}