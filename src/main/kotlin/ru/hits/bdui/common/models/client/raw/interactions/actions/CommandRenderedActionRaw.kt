package ru.hits.bdui.common.models.client.raw.interactions.actions

/**
 * Действие, отвечающее за вызов команд из реестра
 *
 * @property name название команды, которую нужно исполнить
 * @property params передаваемые параметры для исполнения команды
 */
data class CommandRenderedActionRaw(
    val name: String,
    val params: Map<String, String>
) : RenderedActionRaw {
    override val type: String = "command"
}
