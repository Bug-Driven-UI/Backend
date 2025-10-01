package ru.hits.bdui.client.action.controller.raw.actions.request

import com.fasterxml.jackson.databind.JsonNode

/**
 * Действие, отвечающее за вызов команд из реестра
 *
 * @property name название команды, которую нужно исполнить
 * @property params передаваемые параметры для исполнения команды
 */
data class CommandActionRawRequest(
    val name: String,
    val params: Map<String, JsonNode>
) : ActionRawRequest {
    override val type: String = "command"
}