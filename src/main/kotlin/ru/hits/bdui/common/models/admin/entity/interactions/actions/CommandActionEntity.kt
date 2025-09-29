package ru.hits.bdui.common.models.admin.entity.interactions.actions

/**
 * Действие, отвечающее за вызов команд из реестра
 *
 * @property name название команды, которую нужно исполнить
 * @property params передаваемые параметры для исполнения команды
 */
data class CommandActionEntity(
    val name: String,
    val params: Map<String, String>
) : ActionEntity {
    override val type: String = "command"
}