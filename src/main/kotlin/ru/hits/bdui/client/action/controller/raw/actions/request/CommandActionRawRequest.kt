package ru.hits.bdui.client.action.controller.raw.actions.request

/**
 * Действие, отвечающее за вызов команд из реестра
 *
 * @property name название команды, которую нужно исполнить
 * @property params передаваемые параметры для исполнения команды
 */
data class CommandActionRawRequest(
    val name: String,
    val params: Map<String, String>
) : ActionRawRequest {
    override val type: String = "command"
}