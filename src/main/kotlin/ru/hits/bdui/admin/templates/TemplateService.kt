package ru.hits.bdui.admin.templates

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.templates.database.TemplateRepository
import ru.hits.bdui.common.exceptions.AlreadyExistsException
import ru.hits.bdui.common.exceptions.notFound
import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase
import ru.hits.bdui.domain.template.ComponentTemplate
import ru.hits.bdui.domain.template.ComponentTemplateFromDatabase
import ru.hits.bdui.utils.isUniqueViolation
import java.time.Instant
import java.util.UUID

interface TemplateService {
    fun save(template: ComponentTemplate): Mono<ComponentTemplateFromDatabase>
    fun findById(id: UUID): Mono<ComponentTemplateFromDatabase>
    fun findByName(name: String): Mono<ComponentTemplateFromDatabase>
    fun delete(id: UUID): Mono<Unit>
    fun update(templateFromDatabase: ComponentTemplateFromDatabase): Mono<ComponentTemplateFromDatabase>
    fun findAllLikeName(name: String): Mono<List<ComponentTemplateFromDatabase>>
}

@Service
class TemplateServiceImpl(
    private val repository: TemplateRepository
) : TemplateService {
    @Transactional
    override fun save(template: ComponentTemplate): Mono<ComponentTemplateFromDatabase> =
        repository.save(template)
            .handleSaveResponse()

    @Transactional(readOnly = true)
    override fun findById(id: UUID): Mono<ComponentTemplateFromDatabase> =
        repository.findById(id)
            .map { response ->
                when (response) {
                    is TemplateRepository.FindResponse.Found ->
                        response.template

                    is TemplateRepository.FindResponse.NotFound ->
                        throw notFound<ComponentTemplateFromDatabase>(id)
                }
            }

    @Transactional(readOnly = true)
    override fun findByName(name: String): Mono<ComponentTemplateFromDatabase> =
        repository.findByName(name)
            .map { response ->
                when (response) {
                    is TemplateRepository.FindResponse.Found ->
                        response.template

                    is TemplateRepository.FindResponse.NotFound ->
                        throw notFound<ComponentTemplateFromDatabase>(name)
                }
            }

    @Transactional
    override fun delete(id: UUID): Mono<Unit> =
        repository.delete(id)

    @Transactional
    override fun update(templateFromDatabase: ComponentTemplateFromDatabase): Mono<ComponentTemplateFromDatabase> =
        repository.existsById(templateFromDatabase.id)
            .flatMap { exists ->
                if (exists) {
                    val updatedTemplate = templateFromDatabase.copy(
                        lastModifiedTimestampMs = Instant.now().toEpochMilli()
                    )
                    repository.update(updatedTemplate)
                } else {
                    notFound<ColorStyleFromDatabase>(templateFromDatabase.id).toMono()
                }
            }
            .handleSaveResponse()

    @Transactional(readOnly = true)
    override fun findAllLikeName(name: String): Mono<List<ComponentTemplateFromDatabase>> =
        repository.findAllLikeName(name)
            .map { response ->
                when (response) {
                    is TemplateRepository.FindAllResponse.Success -> response.templates
                    is TemplateRepository.FindAllResponse.Error -> throw response.error
                }
            }

    private fun Mono<TemplateRepository.SaveResponse>.handleSaveResponse(): Mono<ComponentTemplateFromDatabase> =
        this
            .map { response ->
                when (response) {
                    is TemplateRepository.SaveResponse.Success ->
                        response.template

                    is TemplateRepository.SaveResponse.Error ->
                        if (response.error.isUniqueViolation())
                            throw AlreadyExistsException("Шаблон с таким именем уже существует")
                        else
                            throw response.error
                }
            }
}