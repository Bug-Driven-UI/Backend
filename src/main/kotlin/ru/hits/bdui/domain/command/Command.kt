package ru.hits.bdui.domain.command

import ru.hits.bdui.domain.CommandName
import ru.hits.bdui.domain.api.ApiCallRepresentation
import ru.hits.bdui.domain.template.ComponentTemplateFromDatabase

/**
 * Представление команды из реестра
 *
 * @property name название команды
 * @property commandParams параметры, требующиеся для исполнения команды
 * @property apis внешние API, требующиеся для исполнения команды
 * @property itemTemplate шаблон для заполнения ответа от команды (если указан)
 * @property fallbackMessage сообщение об ошибке, в случае неудачного исполнения команды
 */
data class Command(
    val name: CommandName,
    val commandParams: Set<String>,
    val apis: Map<String, ApiCallRepresentation>,
    val itemTemplate: ComponentTemplateFromDatabase?,
    val fallbackMessage: String?
)