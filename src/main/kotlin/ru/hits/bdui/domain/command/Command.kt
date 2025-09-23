package ru.hits.bdui.domain.command

import ru.hits.bdui.domain.CommandName
import ru.hits.bdui.domain.api.ShortApiRepresentation
import ru.hits.bdui.domain.template.ComponentTemplate

/**
 * Представление команды из реестра
 *
 * @param name название команды
 * @param commandParams параметры, требующиеся для исполнения команды
 * @param apis внешние API, требующиеся для исполнения команды
 * @param itemTemplate шаблон для заполнения ответа от команды (если указан)
 * @param fallbackOnErrorMessage сообщение об ошибке, в случае неудачного исполнения команды
 */
data class Command(
    val name: CommandName,
    val commandParams: Set<String>,
    val apis: Map<String, ShortApiRepresentation>,
    val itemTemplate: ComponentTemplate?,
    val fallbackOnErrorMessage: String?
)