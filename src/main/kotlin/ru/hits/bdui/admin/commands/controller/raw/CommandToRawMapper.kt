package ru.hits.bdui.admin.commands.controller.raw

import ru.hits.bdui.admin.screen.controller.raw.ApiCallRepresentationRaw
import ru.hits.bdui.admin.screen.controller.raw.ParamRaw
import ru.hits.bdui.domain.command.CommandFromDatabase

fun CommandFromDatabaseRaw.Companion.emerge(data: CommandFromDatabase): CommandFromDatabaseRaw =
    CommandFromDatabaseRaw(
        id = data.id,
        name = data.command.name.value,
        apis = data.command.apis.map { api ->
            ApiCallRepresentationRaw(
                id = api.value.apiId,
                alias = api.value.apiResultAlias,
                params = api.value.apiParams.map { entry ->
                    ParamRaw(
                        name = entry.key,
                        value = entry.value
                    )
                }
            )
        },
        params = data.command.commandParams,
        itemTemplateId = data.command.itemTemplate?.id,
        fallbackMessage = data.command.fallbackMessage,
        createdAtTimestampMs = data.createdAtTimestampMs,
        lastModifiedAtTimestampMs = data.lastModifiedTimestampMs,
    )
