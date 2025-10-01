package ru.hits.bdui.common.models.admin.entity.command

import ru.hits.bdui.common.models.admin.entity.components.ComponentTemplateFromDatabaseEntity
import ru.hits.bdui.common.models.admin.entity.screen.ApiCallRepresentationEntity

/**
 * Представление команды из реестра
 *
 * @property name название команды
 * @property commandParams параметры, требующиеся для исполнения команды
 * @property apis внешние API, требующиеся для исполнения команды
 * @property itemTemplate шаблон для заполнения ответа от команды (если указан)
 * @property fallbackMessage сообщение об ошибке, в случае неудачного исполнения команды
 */
data class CommandEntityJson(
    val name: String,
    val commandParams: Set<String>,
    val apis: Map<String, ApiCallRepresentationEntity>,
    val itemTemplate: ComponentTemplateFromDatabaseEntity?,
    val fallbackMessage: String?
)