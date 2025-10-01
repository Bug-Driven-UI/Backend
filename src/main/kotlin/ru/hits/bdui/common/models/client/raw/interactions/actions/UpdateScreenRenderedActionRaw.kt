package ru.hits.bdui.common.models.client.raw.interactions.actions

/**
 * Действие, отвечающее за обновление экрана
 *
 * @property screenName название экрана
 * @property screenNavigationParams параметры для обновления экрана
 */
data class UpdateScreenRenderedActionRaw(
    val screenName: String,
    val screenNavigationParams: Map<String, String>
) : RenderedActionRaw {
    override val type: String = "updateScreen"
}
