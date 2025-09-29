package ru.hits.bdui.common.models.admin.entity.interactions.actions

/**
 * Действие, отвечающее за обновление экрана
 *
 * @property screenName название экрана
 * @property screenNavigationParams параметры для обновления экрана
 */
data class UpdateScreenActionEntity(
    val screenName: String,
    val screenNavigationParams: Map<String, String>
) : ActionEntity {
    override val type: String = "updateScreen"
}