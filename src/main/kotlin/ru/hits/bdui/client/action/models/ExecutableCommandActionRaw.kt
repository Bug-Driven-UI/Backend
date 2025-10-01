package ru.hits.bdui.client.action.models

/**
 * Действие, отвечающее за вызов команд из реестра
 *
 * @property name название команды, которую нужно исполнить
 * @property params передаваемые параметры для исполнения команды
 */
data class ExecutableCommandActionRaw(
    val name: String,
    val params: Map<String, String>
) : ExecutableActionRaw {
    override val type: String = "command"
}
