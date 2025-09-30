package ru.hits.bdui.admin.screen.controller.raw.utils

import ru.hits.bdui.admin.screen.controller.raw.ApiCallRepresentationRaw
import ru.hits.bdui.admin.screen.controller.raw.ParamRaw
import ru.hits.bdui.admin.screen.controller.raw.ScaffoldRaw
import ru.hits.bdui.admin.screen.controller.raw.ScreenFromDatabaseRaw
import ru.hits.bdui.admin.screen.controller.raw.ScreenVersionRaw
import ru.hits.bdui.common.models.admin.raw.utils.toRaw
import ru.hits.bdui.domain.screen.ScreenFromDatabase
import ru.hits.bdui.domain.screen.ScreenVersion

fun ScreenFromDatabaseRaw.Companion.emerge(screen: ScreenFromDatabase): ScreenFromDatabaseRaw =
    ScreenFromDatabaseRaw(
        screenId = screen.meta.id.value,
        screenName = screen.meta.name.value,
        version = ScreenVersionRaw(
            id = screen.versionId,
            version = screen.version,
            isProduction = screen.versionId == screen.meta.versionId,
            createdAtTimestampMs = screen.createdAtTimestampMs,
            lastModifiedTimestampMs = screen.lastModifiedAtTimestampMs
        ),
        description = screen.meta.description,
        screenNavigationParams = screen.screen.screenNavigationParams,
        apis = screen.screen.apis.map { api ->
            ApiCallRepresentationRaw(
                id = api.apiId,
                alias = api.apiResultAlias,
                params = api.apiParams.map { entry ->
                    ParamRaw(
                        name = entry.key,
                        value = entry.value
                    )
                }
            )
        },
        components = screen.screen.components.map { it.toRaw() },
        scaffold = ScaffoldRaw(
            topBar = screen.screen.scaffold?.topBar?.toRaw(),
            bottomBar = screen.screen.scaffold?.bottomBar?.toRaw(),
        )
    )

fun ScreenVersionRaw.Companion.emerge(version: ScreenVersion) =
    ScreenVersionRaw(
        id = version.id,
        version = version.version,
        isProduction = version.isProduction,
        createdAtTimestampMs = version.createdAtTimestampMs,
        lastModifiedTimestampMs = version.lastModifiedTimestampMs
    )