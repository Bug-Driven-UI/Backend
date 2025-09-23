package ru.hits.bdui.domain.screen.interactions.actions

import ru.hits.bdui.domain.CommandName

/**
 * Действие, отвечающее за вызов команд из реестра
 *
 * @param name название команды, которую нужно исполнить
 * @param params переданные параметры для исполнения команды
 */
data class CommandAction(
    val name: CommandName,
    val params: Map<String, String>
) : Action {
    override val type: String = "command"
}