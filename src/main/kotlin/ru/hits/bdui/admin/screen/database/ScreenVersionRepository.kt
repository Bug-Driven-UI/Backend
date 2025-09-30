package ru.hits.bdui.admin.screen.database

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.screen.database.ScreenMetaRepository.FindAllResponse
import ru.hits.bdui.admin.screen.database.ScreenMetaRepository.FindResponse
import ru.hits.bdui.admin.screen.database.ScreenMetaRepository.SaveResponse
import ru.hits.bdui.admin.screen.database.emerge.emerge
import ru.hits.bdui.admin.screen.database.entity.ScreenMetaEntity
import ru.hits.bdui.admin.screen.database.entity.ScreenVersionEntity
import ru.hits.bdui.admin.screen.database.repository.ScreenVersionJpaRepository
import ru.hits.bdui.admin.screen.models.ScreenUpdateCommand
import ru.hits.bdui.domain.screen.Screen
import ru.hits.bdui.domain.screen.ScreenFromDatabase
import ru.hits.bdui.domain.screen.meta.ScreenMetaFromDatabase
import java.util.UUID

interface ScreenVersionRepository {
    fun findById(id: UUID): Mono<FindResponse>

    sealed interface FindResponse {
        data class Found(val screen: ScreenFromDatabase) : FindResponse
        data object NotFound : FindResponse
    }

    fun save(screen: Screen): Mono<SaveResponse>

    fun update(
        meta: ScreenMetaFromDatabase,
        updateCommand: ScreenUpdateCommand
    ): Mono<SaveResponse>

    sealed interface SaveResponse {
        data class Success(val screen: ScreenMetaFromDatabase) : SaveResponse
        data class Error(val error: Throwable) : SaveResponse
    }

    fun findAllLikeName(name: String): Mono<FindAllResponse>

    sealed interface FindAllResponse {
        data class Success(val screens: List<ScreenMetaFromDatabase>) : FindAllResponse
        data class Error(val error: Throwable) : FindAllResponse
    }

    fun existsById(id: UUID): Mono<Boolean>
}

@Repository
class ScreenVersionRepositoryImpl(
    private val repository: ScreenVersionJpaRepository
) : ScreenMetaRepository {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional(readOnly = true)
    override fun findById(id: UUID): Mono<FindResponse> =
        Mono.fromCallable { repository.findById(id) }
            .map { opt ->
                if (opt.isPresent) FindResponse.Found(ScreenMetaFromDatabase.emerge(opt.get()))
                else FindResponse.NotFound
            }
            .doOnError { e -> log.error("Ошибка при получении экрана по id", e) }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional
    override fun save(screen: Screen): Mono<SaveResponse> {
        val metaEntity = ScreenMetaEntity.emerge(screen)

        return save(metaEntity)
    }

    @Transactional
    override fun update(
        meta: ScreenMetaFromDatabase,
        updateCommand: ScreenUpdateCommand
    ): Mono<SaveResponse> {
        val updatedMeta = meta.copy(
            description = updateCommand.screen.description,
        )
        val entity = ScreenMetaEntity.emerge(updatedMeta)

        return save(entity)
    }

    @Transactional(readOnly = true)
    override fun findAllLikeName(name: String): Mono<FindAllResponse> =
        Mono.fromCallable { repository.findAllLikeName(name) }
            .map { list -> list.map(ScreenMetaFromDatabase::emerge) }
            .map<FindAllResponse>(FindAllResponse::Success)
            .doOnError { error -> log.error("При получении мета данных экранов по имени произошла ошибка", error) }
            .onErrorResume { FindAllResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun existsById(id: UUID): Mono<Boolean> =
        Mono.fromCallable { repository.existsById(id) }
            .doOnError { error -> log.error("При проверке наличия экрана произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())

    private fun save(entity: ScreenVersionEntity): Mono<SaveResponse> =
        Mono.fromCallable { repository.save(entity) }
            .map(ScreenMetaFromDatabase::emerge)
            .map<SaveResponse>(SaveResponse::Success)
            .doOnError { error -> log.error("При сохранении мета данных экрана произошла ошибка", error) }
            .onErrorResume { SaveResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())
}