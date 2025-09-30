package ru.hits.bdui.admin.screen.database.emerge

import ru.hits.bdui.admin.screen.database.entity.ScreenMetaEntity
import ru.hits.bdui.admin.screen.database.entity.ScreenVersionEntity
import ru.hits.bdui.common.models.admin.entity.screen.ApiCallRepresentationEntity
import ru.hits.bdui.common.models.admin.entity.screen.ScaffoldEntity
import ru.hits.bdui.common.models.admin.entity.screen.ScreenEntity
import ru.hits.bdui.common.models.admin.entity.utils.toDomain
import ru.hits.bdui.common.models.admin.entity.utils.toEntity
import ru.hits.bdui.domain.ScreenId
import ru.hits.bdui.domain.ScreenName
import ru.hits.bdui.domain.api.ApiCallRepresentation
import ru.hits.bdui.domain.screen.Scaffold
import ru.hits.bdui.domain.screen.Screen
import ru.hits.bdui.domain.screen.ScreenFromDatabase
import ru.hits.bdui.domain.screen.meta.ScreenMetaFromDatabase
import java.time.Instant
import java.util.UUID

fun ScreenMetaEntity.Companion.emerge(screen: Screen): ScreenMetaEntity =
    ScreenMetaEntity(
        id = UUID.randomUUID(),
        name = screen.screenName.value,
        description = screen.description,
        currentPublishedVersionId = null
    )

fun ScreenMetaEntity.Companion.emerge(meta: ScreenMetaFromDatabase): ScreenMetaEntity =
    ScreenMetaEntity(
        id = meta.id.value,
        name = meta.name.value,
        description = meta.description,
        currentPublishedVersionId = meta.versionId
    )

fun ScreenMetaFromDatabase.Companion.emerge(entity: ScreenMetaEntity): ScreenMetaFromDatabase =
    ScreenMetaFromDatabase(
        id = ScreenId(entity.id),
        name = ScreenName(entity.name),
        description = entity.description,
        versionId = entity.currentPublishedVersionId
    )

fun ScreenVersionEntity.Companion.emerge(data: ScreenFromDatabase): ScreenVersionEntity =
    ScreenVersionEntity(
        id = data.versionId,
        meta = ScreenMetaEntity.emerge(data.meta),
        version = data.version,
        screen = ScreenEntity.emerge(data.screen),
        createdAtTimestampMs = data.createdAtTimestampMs,
        lastModifiedAtTimestampMs = Instant.now().toEpochMilli(),
    )

fun ScreenFromDatabase.Companion.emerge(entity: ScreenVersionEntity): ScreenFromDatabase =
    ScreenFromDatabase(
        meta = ScreenMetaFromDatabase.emerge(entity.meta),
        versionId = entity.id,
        version = entity.version,
        screen = Screen.emerge(entity.screen),
        createdAtTimestampMs = entity.createdAtTimestampMs,
        lastModifiedAtTimestampMs = entity.lastModifiedAtTimestampMs,
    )

private fun ScreenEntity.Companion.emerge(screen: Screen): ScreenEntity =
    ScreenEntity(
        screenName = screen.screenName.value,
        description = screen.description,
        screenNavigationParams = screen.screenNavigationParams,
        apis = screen.apis.map {
            ApiCallRepresentationEntity(
                apiResultAlias = it.apiResultAlias,
                apiId = it.apiId,
                apiParams = it.apiParams
            )
        },
        components = screen.components.map { it.toEntity() },
        scaffold = ScaffoldEntity(
            topBar = screen.scaffold?.topBar?.toEntity(),
            bottomBar = screen.scaffold?.bottomBar?.toEntity(),
        )
    )

fun Screen.Companion.emerge(screen: ScreenEntity): Screen =
    Screen(
        screenName = ScreenName(screen.screenName),
        description = screen.description,
        screenNavigationParams = screen.screenNavigationParams,
        apis = screen.apis.map {
            ApiCallRepresentation(
                apiResultAlias = it.apiResultAlias,
                apiId = it.apiId,
                apiParams = it.apiParams
            )
        },
        components = screen.components.map { it.toDomain() },
        scaffold = Scaffold(
            topBar = screen.scaffold?.topBar?.toDomain(),
            bottomBar = screen.scaffold?.bottomBar?.toDomain()
        )
    )