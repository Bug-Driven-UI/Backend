package ru.hits.bdui.client.action.controller.raw.actions.request

/**
 * Действие, отвечающее за обновление экрана
 *
 * @property screenName название экрана
 * @property screenNavigationParams параметры для обновления экрана
 */
data class UpdateScreenActionRawRequest(
    val screenName: String,
    val screenNavigationParams: Map<String, String>,
    val hashes: List<HashNode>
) : ActionRawRequest {
    override val type: String = "updateScreen"
}

data class HashNode(
    val id: String,
    val hash: String,
    val children: List<HashNode>?
)