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
import java.time.Instant
import java.util.UUID

interface ScreenService {
    fun findAllVersions(screenId: ScreenId): Mono<List<ScreenVersion>>
    fun findAllLikeName(name: String): Mono<List<ScreenMetaFromDatabase>>
    fun setProductionVersion(screenId: ScreenId, versionId: UUID): Mono<ScreenVersion>
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
                    is ScreenMetaRepository.FindAllResponse.Success -> response.metas
                    is ScreenMetaRepository.FindAllResponse.Error -> throw response.error
                }
            }

    @Transactional
    override fun setProductionVersion(screenId: ScreenId, versionId: UUID): Mono<ScreenVersion> =
        metaRepository.findById(screenId)
            .map { response ->
                when (response) {
                    is ScreenMetaRepository.FindResponse.Found ->
                        response.meta

                    is ScreenMetaRepository.FindResponse.NotFound ->
                        throw notFound<ScreenMetaFromDatabase>(screenId.value)
                }
            }
            .flatMap { meta ->
                versionRepository.findById(screenId, versionId)
                    .map { response ->
                        when (response) {
                            is ScreenVersionRepository.FindResponse.Found ->
                                response.screen

                            is ScreenVersionRepository.FindResponse.NotFound ->
                                throw notFound<ScreenFromDatabase>(versionId)
                        }
                    }
                    .flatMap { screen ->
                        metaRepository.setProductionVersion(screenId, versionId)
                            .map {
                                ScreenVersion(
                                    id = screen.versionId,
                                    version = screen.version,
                                    isProduction = true,
                                    createdAtTimestampMs = screen.createdAtTimestampMs,
                                    lastModifiedTimestampMs = screen.lastModifiedAtTimestampMs
                                )
                            }
                    }
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
                            screen = command.screen,
                            lastModifiedAtTimestampMs = Instant.now().toEpochMilli(),
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
    override fun save(screen: Screen): Mono<ScreenFromDatabase> =
        metaRepository.findByName(screen.screenName)
            .flatMap { response ->
                when (response) {
                    is ScreenMetaRepository.FindResponse.Found ->
                        saveNewVersion(response.meta, screen)

                    is ScreenMetaRepository.FindResponse.NotFound ->
                        saveNewScreen(screen)
                }
            }
            .map { response ->
                when (response) {
                    is ScreenVersionRepository.SaveResponse.Success ->
                        response.screen

                    is ScreenVersionRepository.SaveResponse.Error ->
                        throw response.error
                }
            }

    private fun saveNewScreen(screen: Screen): Mono<ScreenVersionRepository.SaveResponse> =
        metaRepository.save(screen)
            .map { response ->
                when (response) {
                    is ScreenMetaRepository.SaveResponse.Success ->
                        response.meta

                    is ScreenMetaRepository.SaveResponse.Error ->
                        throw response.error
                }
            }
            .flatMap { meta ->
                val screenFromDatabase = newVersion(
                    meta = meta,
                    version = 1,
                    screen = screen,
                )

                versionRepository.save(screenFromDatabase)
            }


    private fun saveNewVersion(
        meta: ScreenMetaFromDatabase,
        screen: Screen
    ): Mono<ScreenVersionRepository.SaveResponse> =
        versionRepository.findLatest(meta.id)
            .map { response ->
                when (response) {
                    is ScreenVersionRepository.FindResponse.Found ->
                        newVersion(
                            meta = meta,
                            version = response.screen.version + 1,
                            screen = screen,
                        )

                    is ScreenVersionRepository.FindResponse.NotFound ->
                        newVersion(
                            meta = meta,
                            version = 1,
                            screen = screen,
                        )
                }
            }
            .flatMap { versionRepository.save(it) }

    private fun newVersion(meta: ScreenMetaFromDatabase, version: Int, screen: Screen) =
        ScreenFromDatabase(
            meta = meta,
            versionId = UUID.randomUUID(),
            version = version,
            screen = screen,
            createdAtTimestampMs = Instant.now().toEpochMilli(),
            lastModifiedAtTimestampMs = null,
            rowVersion = null
        )
}