package ru.hits.bdui.textStyles.database

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextStyleFromDatabase
import ru.hits.bdui.textStyles.database.TextStyleRepository.FindAllResponse
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
        data class Found(val textStyle: TextStyleFromDatabase) : FindResponse
        data object NotFound : FindResponse
    }

    fun save(textStyle: TextStyle): Mono<SaveResponse>

    fun update(textStyle: TextStyleFromDatabase): Mono<SaveResponse>

    sealed interface SaveResponse {
        data class Success(val textStyle: TextStyleFromDatabase) : SaveResponse
        data class Error(val error: Throwable) : SaveResponse
    }

    fun delete(id: UUID): Mono<Unit>

    fun findAllLikeToken(token: String): Mono<FindAllResponse>

    sealed interface FindAllResponse {
        data class Success(val textStyles: List<TextStyleFromDatabase>) : FindAllResponse
        data class Error(val error: Throwable) : FindAllResponse
    }

    fun existsById(id: UUID): Mono<Boolean>
}

@Repository
class TextStyleRepositoryImpl(
    private val repository: TextStyleJpaRepository
) : TextStyleRepository {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun findById(id: UUID): Mono<FindResponse> =
        Mono.fromCallable { repository.findById(id) }
            .map { result ->
                if (result.isPresent) {
                    val textStyle = TextStyleFromDatabase.emerge(result.get())
                    FindResponse.Found(textStyle)
                } else {
                    FindResponse.NotFound
                }
            }
            .doOnError { error -> log.error("При получении стиля текста произошла ошибка", error) }
            .onErrorResume { FindResponse.NotFound.toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun findByToken(token: String): Mono<FindResponse> =
        Mono.fromCallable { repository.findByToken(token) }
            .map { result ->
                if (result.isPresent) {
                    val textStyle = TextStyleFromDatabase.emerge(result.get())
                    FindResponse.Found(textStyle)
                } else {
                    FindResponse.NotFound
                }
            }
            .doOnError { error -> log.error("При получении стиля текста по токену произошла ошибка", error) }
            .onErrorResume { FindResponse.NotFound.toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun save(textStyle: TextStyle): Mono<SaveResponse> {
        val entity = TextStyleEntity.emerge(textStyle)

        return save(entity)
    }

    override fun update(textStyle: TextStyleFromDatabase): Mono<SaveResponse> {
        val entity = TextStyleEntity.emerge(textStyle)

        return save(entity)
    }

    private fun save(entity: TextStyleEntity): Mono<SaveResponse> =
        Mono.fromCallable { repository.save(entity) }
            .map(TextStyleFromDatabase::emerge)
            .map<SaveResponse>(SaveResponse::Success)
            .doOnError { error -> log.error("При сохранении стиля текста произошла ошибка", error) }
            .onErrorResume { SaveResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun delete(id: UUID): Mono<Unit> =
        Mono.fromCallable { repository.deleteById(id) }
            .subscribeOn(Schedulers.boundedElastic())

    override fun findAllLikeToken(token: String): Mono<FindAllResponse> =
        Mono.fromCallable { repository.findAllLikeTokens(token) }
            .map { list -> list.map(TextStyleFromDatabase::emerge) }
            .map<FindAllResponse>(FindAllResponse::Success)
            .doOnError { error -> log.error("При получении стилей текста по токену произошла ошибка", error) }
            .onErrorResume { FindAllResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun existsById(id: UUID): Mono<Boolean> =
        Mono.fromCallable { repository.existsById(id) }
            .doOnError { error -> log.error("При проверке наличия текстового стиля произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())
}