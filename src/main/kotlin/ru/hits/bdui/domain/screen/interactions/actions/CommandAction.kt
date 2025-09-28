package ru.hits.bdui.domain.screen.interactions.actions

import ru.hits.bdui.domain.CommandName
import ru.hits.bdui.domain.ValueOrExpression

/**
 * Действие, отвечающее за вызов команд из реестра
 *
 * @property name название команды, которую нужно исполнить
 * @property params передаваемые параметры для исполнения команды
 */
data class CommandAction(
    val name: CommandName,
    val params: Map<String, ValueOrExpression>
) : Action {
    override val type: String = "command"
}