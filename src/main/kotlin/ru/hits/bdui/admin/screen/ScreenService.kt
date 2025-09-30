package ru.hits.bdui.admin.screen

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.screen.database.ScreenMetaRepository
import ru.hits.bdui.admin.screen.database.ScreenVersionRepository
import ru.hits.bdui.admin.screen.models.ScreenUpdateCommand
import ru.hits.bdui.common.exceptions.notFound
import ru.hits.bdui.domain.ScreenId
import ru.hits.bdui.domain.screen.Screen
import ru.hits.bdui.domain.screen.ScreenFromDatabase
import ru.hits.bdui.domain.screen.ScreenVersion
import ru.hits.bdui.domain.screen.meta.ScreenMetaFromDatabase
import java.util.UUID

interface ScreenService {
    fun findAllVersions(screenId: ScreenId): Mono<List<ScreenVersion>>
    fun findAllLikeName(name: String): Mono<List<ScreenMetaFromDatabase>>
    fun setProductionVersion(screenId: ScreenId, versionId: UUID): Mono<ScreenMetaFromDatabase>
    fun find(screenId: ScreenId, versionId: UUID): Mono<ScreenFromDatabase>
    fun update(command: ScreenUpdateCommand): Mono<ScreenFromDatabase>
    fun save(screen: Screen): Mono<ScreenFromDatabase>
}

@Repository
class ScreenServiceImpl(
    private val metaRepository: ScreenMetaRepository,
    private val versionRepository: ScreenVersionRepository,
) : ScreenService {
    @Transactional(readOnly = true)
    override fun findAllVersions(screenId: ScreenId): Mono<List<ScreenVersion>> =
        versionRepository.findAllVersions(screenId)
            .map { response ->
                when (response) {
                    is ScreenVersionRepository.FindAllResponse.Success ->
                        response.screens.map { screen ->
                            ScreenVersion(
                                id = screen.versionId,
                                version = screen.version,
                                isProduction = screen.meta.versionId == screen.versionId,
                                createdAtTimestampMs = screen.createdAtTimestampMs,
                                lastModifiedTimestampMs = screen.lastModifiedAtTimestampMs
                            )
                        }

                    is ScreenVersionRepository.FindAllResponse.Error ->
                        throw response.error
                }
            }

    @Transactional(readOnly = true)
    override fun findAllLikeName(name: String): Mono<List<ScreenMetaFromDatabase>> =
        metaRepository.findAllLikeName(name)
            .map { response ->
                when (response) {
                    is ScreenMetaRepository.FindAllResponse.Success -> response.screens
                    is ScreenMetaRepository.FindAllResponse.Error -> throw response.error
                }
            }

    @Transactional
    override fun setProductionVersion(screenId: ScreenId, versionId: UUID): Mono<ScreenMetaFromDatabase> {
        TODO("Not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun find(screenId: ScreenId, versionId: UUID): Mono<ScreenFromDatabase> =
        versionRepository.findById(screenId, versionId)
            .map { response ->
                when (response) {
                    is ScreenVersionRepository.FindResponse.Found ->
                        response.screen

                    is ScreenVersionRepository.FindResponse.NotFound ->
                        throw notFound<ScreenFromDatabase>(versionId)
                }
            }

    @Transactional
    override fun update(command: ScreenUpdateCommand): Mono<ScreenFromDatabase> =
        versionRepository.findById(command.screenId, command.versionId)
            .map { response ->
                when (response) {
                    is ScreenVersionRepository.FindResponse.Found ->
                        response.screen.copy(
                            screen = command.screen
                        )

                    is ScreenVersionRepository.FindResponse.NotFound ->
                        throw notFound<ScreenFromDatabase>(command.versionId)
                }
            }
            .flatMap { versionRepository.update(it) }
            .map { response ->
                when (response) {
                    is ScreenVersionRepository.SaveResponse.Success ->
                        response.screen

                    is ScreenVersionRepository.SaveResponse.Error ->
                        throw response.error
                }
            }

    @Transactional
    override fun save(screen: Screen): Mono<ScreenFromDatabase> {
        TODO("Not yet implemented")
    }
}