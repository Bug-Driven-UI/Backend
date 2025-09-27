package ru.hits.bdui.textStyles.database

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.domain.screen.styles.TextStyle
import ru.hits.bdui.textStyles.database.TextStyleRepository.FindResponse
import ru.hits.bdui.textStyles.database.TextStyleRepository.SaveResponse
import ru.hits.bdui.textStyles.database.emerge.emerge
import ru.hits.bdui.textStyles.database.entity.TextStyleEntity
import ru.hits.bdui.textStyles.database.repository.TextStyleJpaRepository
import java.util.UUID

interface TextStyleRepository {
    fun findById(id: UUID): Mono<FindResponse>

    fun findByToken(token: String): Mono<FindResponse>

    sealed interface FindResponse {
        data class Found(val entity: TextStyleEntity) : FindResponse
        data object NotFound : FindResponse
    }

    fun save(textStyle: TextStyle): Mono<SaveResponse>

    fun update(id: UUID, textStyle: TextStyle): Mono<SaveResponse>

    sealed interface SaveResponse {
        data class Success(val entity: TextStyleEntity) : SaveResponse
        data class Error(val error: Throwable) : SaveResponse
    }

    fun delete(id: UUID): Mono<Unit>

}

@Repository
class TextStyleRepositoryImpl(
    private val repository: TextStyleJpaRepository
) : TextStyleRepository {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun findById(id: UUID): Mono<FindResponse> =
        Mono.fromCallable { repository.findById(id) }
            .map { result ->
                if (result.isPresent)
                    FindResponse.Found(result.get())
                else
                    FindResponse.NotFound
            }
            .doOnError { error -> log.error("При получении стиля текста произошла ошибка", error) }
            .onErrorResume { FindResponse.NotFound.toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun findByToken(token: String): Mono<FindResponse> =
        Mono.fromCallable { repository.findByToken(token) }
            .map { result ->
                if (result.isPresent)
                    FindResponse.Found(result.get())
                else
                    FindResponse.NotFound
            }
            .doOnError { error -> log.error("При получении стиля текста по токену произошла ошибка", error) }
            .onErrorResume { FindResponse.NotFound.toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun save(textStyle: TextStyle): Mono<SaveResponse> {
        val entity = TextStyleEntity.emerge(textStyle)

        return Mono.fromCallable { repository.save(entity) }
            .map<SaveResponse>(SaveResponse::Success)
            .doOnError { error -> log.error("При сохранении стиля текста произошла ошибка", error) }
            .onErrorResume { SaveResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())
    }

    override fun update(id: UUID, textStyle: TextStyle): Mono<SaveResponse> {
        val entity = TextStyleEntity.emerge(id, textStyle)

        return Mono.fromCallable { repository.save(entity) }
            .map<SaveResponse>(SaveResponse::Success)
            .doOnError { error -> log.error("При сохранении стиля текста произошла ошибка", error) }
            .onErrorResume { SaveResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())
    }

    override fun delete(id: UUID): Mono<Unit> =
        Mono.fromCallable { repository.deleteById(id) }
            .subscribeOn(Schedulers.boundedElastic())
}