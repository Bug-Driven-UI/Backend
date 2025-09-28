package ru.hits.bdui.textStyles

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextStyleFromDatabase
import ru.hits.bdui.exceptions.notFound
import ru.hits.bdui.textStyles.database.TextStyleRepository
import java.util.UUID

interface TextStyleService {
    fun save(textStyle: TextStyle): Mono<TextStyleFromDatabase>
    fun findById(id: UUID): Mono<TextStyleFromDatabase>
    fun findByToken(token: String): Mono<TextStyleFromDatabase>
    fun delete(id: UUID): Mono<Unit>
    fun update(textStyleFromDatabase: TextStyleFromDatabase): Mono<TextStyleFromDatabase>
    fun findAllLikeToken(token: String): Mono<List<TextStyleFromDatabase>>
}

@Service
class TextStylesServiceImpl(
    private val repository: TextStyleRepository
) : TextStyleService {
    @Transactional
    override fun save(textStyle: TextStyle): Mono<TextStyleFromDatabase> =
        repository.save(textStyle)
            .map { response ->
                when (response) {
                    is TextStyleRepository.SaveResponse.Success ->
                        response.textStyle

                    is TextStyleRepository.SaveResponse.Error ->
                        throw response.error
                }
            }

    @Transactional(readOnly = true)
    override fun findById(id: UUID): Mono<TextStyleFromDatabase> =
        repository.findById(id)
            .map { response ->
                when (response) {
                    is TextStyleRepository.FindResponse.Found ->
                        response.textStyle

                    is TextStyleRepository.FindResponse.NotFound ->
                        throw notFound<TextStyleFromDatabase>(id)
                }
            }


    @Transactional(readOnly = true)
    override fun findByToken(token: String): Mono<TextStyleFromDatabase> =
        repository.findByToken(token)
            .map { response ->
                when (response) {
                    is TextStyleRepository.FindResponse.Found ->
                        response.textStyle

                    is TextStyleRepository.FindResponse.NotFound ->
                        throw notFound<TextStyleFromDatabase>(token)
                }
            }

    @Transactional
    override fun delete(id: UUID): Mono<Unit> =
        repository.delete(id)

    @Transactional
    override fun update(textStyleFromDatabase: TextStyleFromDatabase): Mono<TextStyleFromDatabase> =
        repository.existsById(textStyleFromDatabase.id)
            .flatMap { exists ->
                if (exists)
                    repository.update(textStyleFromDatabase)
                else
                    notFound<TextStyleFromDatabase>(textStyleFromDatabase.id).toMono()
            }
            .map { response ->
                when (response) {
                    is TextStyleRepository.SaveResponse.Success ->
                        response.textStyle

                    is TextStyleRepository.SaveResponse.Error ->
                        throw response.error
                }
            }

    @Transactional(readOnly = true)
    override fun findAllLikeToken(token: String): Mono<List<TextStyleFromDatabase>> =
        repository.findAllLikeToken(token)
            .map { response ->
                when (response) {
                    is TextStyleRepository.FindAllResponse.Success ->
                        response.textStyles

                    is TextStyleRepository.FindAllResponse.Error ->
                        throw response.error
                }
            }
}