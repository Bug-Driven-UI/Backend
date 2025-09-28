package ru.hits.bdui.admin.colorStyles

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.colorStyles.database.ColorStyleRepository
import ru.hits.bdui.common.exceptions.AlreadyExistsException
import ru.hits.bdui.common.exceptions.notFound
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase
import ru.hits.bdui.utils.isUniqueViolation
import java.util.UUID

interface ColorStyleService {
    fun save(colorStyle: ColorStyle): Mono<ColorStyleFromDatabase>
    fun findById(id: UUID): Mono<ColorStyleFromDatabase>
    fun findByToken(token: String): Mono<ColorStyleFromDatabase>
    fun delete(id: UUID): Mono<Unit>
    fun update(colorStyleFromDatabase: ColorStyleFromDatabase): Mono<ColorStyleFromDatabase>
    fun findAllLikeToken(token: String): Mono<List<ColorStyleFromDatabase>>
}

@Service
class ColorStylesServiceImpl(
    private val repository: ColorStyleRepository
) : ColorStyleService {
    @Transactional
    override fun save(colorStyle: ColorStyle): Mono<ColorStyleFromDatabase> =
        repository.save(colorStyle)
            .handleSaveResponse()

    @Transactional(readOnly = true)
    override fun findById(id: UUID): Mono<ColorStyleFromDatabase> =
        repository.findById(id)
            .map { response ->
                when (response) {
                    is ColorStyleRepository.FindResponse.Found ->
                        response.colorStyle

                    is ColorStyleRepository.FindResponse.NotFound ->
                        throw notFound<ColorStyleFromDatabase>(id)
                }
            }


    @Transactional(readOnly = true)
    override fun findByToken(token: String): Mono<ColorStyleFromDatabase> =
        repository.findByToken(token)
            .map { response ->
                when (response) {
                    is ColorStyleRepository.FindResponse.Found ->
                        response.colorStyle

                    is ColorStyleRepository.FindResponse.NotFound ->
                        throw notFound<ColorStyleFromDatabase>(token)
                }
            }

    @Transactional
    override fun delete(id: UUID): Mono<Unit> =
        repository.delete(id)

    @Transactional
    override fun update(colorStyleFromDatabase: ColorStyleFromDatabase): Mono<ColorStyleFromDatabase> =
        repository.existsById(colorStyleFromDatabase.id)
            .flatMap { exists ->
                if (exists)
                    repository.update(colorStyleFromDatabase)
                else
                    notFound<ColorStyleFromDatabase>(colorStyleFromDatabase.id).toMono()
            }
            .handleSaveResponse()

    private fun Mono<ColorStyleRepository.SaveResponse>.handleSaveResponse(): Mono<ColorStyleFromDatabase> =
        this
            .map { response ->
                when (response) {
                    is ColorStyleRepository.SaveResponse.Success ->
                        response.colorStyle

                    is ColorStyleRepository.SaveResponse.Error ->
                        if (response.error.isUniqueViolation())
                            throw AlreadyExistsException("Стиль цвета с таким токеном уже существует")
                        else
                            throw response.error
                }
            }

    @Transactional(readOnly = true)
    override fun findAllLikeToken(token: String): Mono<List<ColorStyleFromDatabase>> =
        repository.findAllLikeToken(token)
            .map { response ->
                when (response) {
                    is ColorStyleRepository.FindAllResponse.Success ->
                        response.colorStyles

                    is ColorStyleRepository.FindAllResponse.Error ->
                        throw response.error
                }
            }
}