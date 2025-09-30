package ru.hits.bdui.admin.screen

import reactor.core.publisher.Mono
import ru.hits.bdui.admin.screen.models.ScreenUpdateCommand
import ru.hits.bdui.domain.ScreenId
import ru.hits.bdui.domain.screen.Screen
import ru.hits.bdui.domain.screen.ScreenFromDatabase
import ru.hits.bdui.domain.screen.ScreenVersion
import java.util.UUID

interface ScreenService {
    fun findAllVersions(screenId: ScreenId): Mono<List<ScreenVersion>>
    fun findAllLikeName(name: String): Mono<List<ScreenFromDatabase>>
    fun setProductionVersion(screenId: ScreenId, versionId: UUID): Mono<ScreenFromDatabase>
    fun find(screenId: ScreenId, versionId: UUID): Mono<ScreenFromDatabase>
    fun update(command: ScreenUpdateCommand): Mono<ScreenFromDatabase>
    fun save(screen: Screen): Mono<ScreenFromDatabase>
}