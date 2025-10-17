package ru.hits.bdui.client.action.controller.raw.actions.request

import com.fasterxml.jackson.databind.JsonNode

/**
 * Действие, отвечающее за обновление экрана
 *
 * @property screenName название экрана
 * @property screenNavigationParams параметры для обновления экрана
 */
data class UpdateScreenActionRawRequest(
    val screenName: String,
    val screenNavigationParams: Map<String, JsonNode>,
    val screen: ScreenHashes,
    val topBar: ScaffoldHash?,
    val bottomBar: ScaffoldHash?,
) : ActionRawRequest {
    override val type: String = "updateScreen"

    data class ScreenHashes(
        val hashes: List<HashNode>
    )

    data class ScaffoldHash(
        val hash: HashNode
    )
}

data class HashNode(
    val id: String,
    val hash: String,
    val children: List<HashNode>?
)