package ru.hits.bdui.admin.colorStyles.database

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.colorStyles.database.ColorStyleRepository.FindAllResponse
import ru.hits.bdui.admin.colorStyles.database.ColorStyleRepository.FindResponse
import ru.hits.bdui.admin.colorStyles.database.ColorStyleRepository.SaveResponse
import ru.hits.bdui.admin.colorStyles.database.emerge.emerge
import ru.hits.bdui.admin.colorStyles.database.entity.ColorStyleEntity
import ru.hits.bdui.admin.colorStyles.database.repository.ColorStyleJpaRepository
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase
import java.util.UUID

interface ColorStyleRepository {
    fun findById(id: UUID): Mono<FindResponse>

    fun findByToken(token: String): Mono<FindResponse>

    sealed interface FindResponse {
        data class Found(val colorStyle: ColorStyleFromDatabase) : FindResponse
        data object NotFound : FindResponse
    }

    fun save(colorStyle: ColorStyle): Mono<SaveResponse>

    fun update(colorStyle: ColorStyleFromDatabase): Mono<SaveResponse>

    sealed interface SaveResponse {
        data class Success(val colorStyle: ColorStyleFromDatabase) : SaveResponse
        data class Error(val error: Throwable) : SaveResponse
    }

    fun delete(id: UUID): Mono<Unit>

    fun findAllLikeToken(token: String): Mono<FindAllResponse>

    sealed interface FindAllResponse {
        data class Success(val colorStyles: List<ColorStyleFromDatabase>) : FindAllResponse
        data class Error(val error: Throwable) : FindAllResponse
    }

    fun existsById(id: UUID): Mono<Boolean>
}

@Repository
class ColorStyleRepositoryImpl(
    private val repository: ColorStyleJpaRepository
) : ColorStyleRepository {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional(readOnly = true)
    override fun findById(id: UUID): Mono<FindResponse> =
        Mono.fromCallable { repository.findById(id) }
            .map { result ->
                if (result.isPresent) {
                    val colorStyle = ColorStyleFromDatabase.emerge(result.get())
                    FindResponse.Found(colorStyle)
                } else {
                    FindResponse.NotFound
                }
            }
            .doOnError { error -> log.error("При получении стиля текста произошла ошибка", error) }
            .onErrorResume { FindResponse.NotFound.toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun findByToken(token: String): Mono<FindResponse> =
        Mono.fromCallable { repository.findByToken(token) }
            .map { result ->
                if (result.isPresent) {
                    val colorStyle = ColorStyleFromDatabase.emerge(result.get())
                    FindResponse.Found(colorStyle)
                } else {
                    FindResponse.NotFound
                }
            }
            .doOnError { error -> log.error("При получении стиля текста по токену произошла ошибка", error) }
            .onErrorResume { FindResponse.NotFound.toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional
    override fun save(colorStyle: ColorStyle): Mono<SaveResponse> {
        val entity = ColorStyleEntity.emerge(colorStyle)

        return save(entity)
    }

    @Transactional(readOnly = true)
    override fun update(colorStyle: ColorStyleFromDatabase): Mono<SaveResponse> {
        val entity = ColorStyleEntity.emerge(colorStyle)

        return save(entity)
    }

    private fun save(entity: ColorStyleEntity): Mono<SaveResponse> =
        Mono.fromCallable { repository.save(entity) }
            .map(ColorStyleFromDatabase::emerge)
            .map<SaveResponse>(SaveResponse::Success)
            .doOnError { error -> log.error("При сохранении стиля текста произошла ошибка", error) }
            .onErrorResume { SaveResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun delete(id: UUID): Mono<Unit> =
        Mono.fromCallable { repository.deleteById(id) }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun findAllLikeToken(token: String): Mono<FindAllResponse> =
        Mono.fromCallable { repository.findAllLikeTokens(token) }
            .map { list -> list.map(ColorStyleFromDatabase::emerge) }
            .map<FindAllResponse>(FindAllResponse::Success)
            .doOnError { error -> log.error("При получении стилей текста по токену произошла ошибка", error) }
            .onErrorResume { FindAllResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun existsById(id: UUID): Mono<Boolean> =
        Mono.fromCallable { repository.existsById(id) }
            .doOnError { error -> log.error("При проверке наличия текстового стиля произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())
}