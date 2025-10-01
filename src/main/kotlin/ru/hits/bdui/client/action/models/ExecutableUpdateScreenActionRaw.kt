package ru.hits.bdui.client.action.models

/**
 * Действие, отвечающее за обновление экрана
 *
 * @property screenName название экрана
 * @property screenNavigationParams параметры для обновления экрана
 */
data class ExecutableUpdateScreenActionRaw(
    val screenName: String,
    val screenNavigationParams: Map<String, String>
) : ExecutableActionRaw {
    override val type: String = "updateScreen"
}

data class HashNode(
    val id: String,
    val hash: String,
    val children: List<HashNode>
)