package ru.hits.bdui.admin.screen.database

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.screen.database.ScreenVersionRepository.FindAllResponse
import ru.hits.bdui.admin.screen.database.ScreenVersionRepository.FindResponse
import ru.hits.bdui.admin.screen.database.ScreenVersionRepository.SaveResponse
import ru.hits.bdui.admin.screen.database.emerge.emerge
import ru.hits.bdui.admin.screen.database.entity.ScreenVersionEntity
import ru.hits.bdui.admin.screen.database.repository.ScreenVersionJpaRepository
import ru.hits.bdui.domain.ScreenId
import ru.hits.bdui.domain.screen.ScreenFromDatabase
import java.util.UUID

interface ScreenVersionRepository {
    fun findById(screenId: ScreenId, versionId: UUID): Mono<FindResponse>

    sealed interface FindResponse {
        data class Found(val screen: ScreenFromDatabase) : FindResponse
        data object NotFound : FindResponse
    }

    fun save(screen: ScreenFromDatabase): Mono<SaveResponse>

    fun update(screen: ScreenFromDatabase): Mono<SaveResponse>

    fun findAllVersions(screenId: ScreenId): Mono<FindAllResponse>

    sealed interface SaveResponse {
        data class Success(val screen: ScreenFromDatabase) : SaveResponse
        data class Error(val error: Throwable) : SaveResponse
    }

    sealed interface FindAllResponse {
        data class Success(val screens: List<ScreenFromDatabase>) : FindAllResponse
        data class Error(val error: Throwable) : FindAllResponse
    }

    fun existsById(id: UUID): Mono<Boolean>
}

@Repository
class ScreenVersionRepositoryImpl(
    private val repository: ScreenVersionJpaRepository
) : ScreenVersionRepository {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional(readOnly = true)
    override fun findById(screenId: ScreenId, versionId: UUID): Mono<FindResponse> =
        Mono.fromCallable {
            repository.findByIdAndScreenId(
                screenId = screenId.value,
                versionId = versionId
            )
        }
            .map { opt ->
                if (opt.isPresent) FindResponse.Found(ScreenFromDatabase.emerge(opt.get()))
                else FindResponse.NotFound
            }
            .doOnError { e -> log.error("Ошибка при получении экрана по id", e) }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional
    override fun save(screen: ScreenFromDatabase): Mono<SaveResponse> {
        val versionEntity = ScreenVersionEntity.emerge(screen)

        return save(versionEntity)
    }

    @Transactional
    override fun update(screen: ScreenFromDatabase): Mono<SaveResponse> {
        val entity = ScreenVersionEntity.emerge(screen)

        return save(entity)
    }

    override fun findAllVersions(screenId: ScreenId): Mono<FindAllResponse> =
        Mono.fromCallable { repository.findAllVersionsByScreenId(screenId.value) }
            .map { list ->
                list.map(ScreenFromDatabase::emerge)
            }
            .map<FindAllResponse>(FindAllResponse::Success)
            .doOnError { error -> log.error("При получении версий экрана произошла ошибка", error) }
            .onErrorResume { FindAllResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())


    @Transactional(readOnly = true)
    override fun existsById(id: UUID): Mono<Boolean> =
        Mono.fromCallable { repository.existsById(id) }
            .doOnError { error -> log.error("При проверке наличия экрана произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())

    private fun save(entity: ScreenVersionEntity): Mono<SaveResponse> =
        Mono.fromCallable { repository.save(entity) }
            .map(ScreenFromDatabase::emerge)
            .map<SaveResponse>(SaveResponse::Success)
            .doOnError { error -> log.error("При сохранении экрана произошла ошибка", error) }
            .onErrorResume { SaveResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())
}