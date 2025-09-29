package ru.hits.bdui.common.models.admin.raw.interactions.actions

/**
 * Действие, отвечающее за вызов команд из реестра
 *
 * @property name название команды, которую нужно исполнить
 * @property params передаваемые параметры для исполнения команды
 */
data class CommandActionRaw(
    val name: String,
    val params: Map<String, String>
) : ActionRaw {
    override val type: String = "command"
}