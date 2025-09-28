package ru.hits.bdui.admin.templates.database

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.templates.database.TemplateRepository.FindAllResponse
import ru.hits.bdui.admin.templates.database.TemplateRepository.FindResponse
import ru.hits.bdui.admin.templates.database.TemplateRepository.SaveResponse
import ru.hits.bdui.admin.templates.database.emerge.emerge
import ru.hits.bdui.admin.templates.database.entity.TemplateEntity
import ru.hits.bdui.admin.templates.database.repository.ComponentTemplateJpaRepository
import ru.hits.bdui.admin.templates.models.ComponentTemplateUpdateCommand
import ru.hits.bdui.domain.TemplateName
import ru.hits.bdui.domain.template.ComponentTemplate
import ru.hits.bdui.domain.template.ComponentTemplateFromDatabase
import java.util.UUID

interface TemplateRepository {
    fun findById(id: UUID): Mono<FindResponse>

    fun findByName(name: String): Mono<FindResponse>

    sealed interface FindResponse {
        data class Found(val template: ComponentTemplateFromDatabase) : FindResponse
        data object NotFound : FindResponse
    }

    fun save(template: ComponentTemplate): Mono<SaveResponse>

    fun update(
        template: ComponentTemplateFromDatabase,
        updateCommand: ComponentTemplateUpdateCommand
    ): Mono<SaveResponse>

    sealed interface SaveResponse {
        data class Success(val template: ComponentTemplateFromDatabase) : SaveResponse
        data class Error(val error: Throwable) : SaveResponse
    }

    fun delete(id: UUID): Mono<Unit>

    fun findAllLikeName(name: String): Mono<FindAllResponse>

    sealed interface FindAllResponse {
        data class Success(val templates: List<ComponentTemplateFromDatabase>) : FindAllResponse
        data class Error(val error: Throwable) : FindAllResponse
    }

    fun existsById(id: UUID): Mono<Boolean>
}

@Repository
class TemplateRepositoryImpl(
    private val repository: ComponentTemplateJpaRepository
) : TemplateRepository {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional(readOnly = true)
    override fun findById(id: UUID): Mono<FindResponse> =
        Mono.fromCallable { repository.findById(id) }
            .map { result ->
                if (result.isPresent) {
                    val template = ComponentTemplateFromDatabase.emerge(result.get())
                    FindResponse.Found(template)
                } else {
                    FindResponse.NotFound
                }
            }
            .doOnError { error -> log.error("При получении шаблона произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun findByName(name: String): Mono<FindResponse> =
        Mono.fromCallable { repository.findByName(name) }
            .map { result ->
                if (result.isPresent) {
                    val template = ComponentTemplateFromDatabase.emerge(result.get())
                    FindResponse.Found(template)
                } else {
                    FindResponse.NotFound
                }
            }
            .doOnError { error -> log.error("При получении шаблона по имени произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional
    override fun save(template: ComponentTemplate): Mono<SaveResponse> {
        val entity = TemplateEntity.emerge(template)

        return save(entity)
    }

    @Transactional(readOnly = true)
    override fun update(
        template: ComponentTemplateFromDatabase,
        updateCommand: ComponentTemplateUpdateCommand
    ): Mono<SaveResponse> {
        val updatedTemplate = template.copy(
            template = ComponentTemplate(
                name = TemplateName(updateCommand.name),
                component = updateCommand.component
            )
        )
        val entity = TemplateEntity.emerge(updatedTemplate)

        return save(entity)
    }

    private fun save(entity: TemplateEntity): Mono<SaveResponse> =
        Mono.fromCallable { repository.save(entity) }
            .map(ComponentTemplateFromDatabase::emerge)
            .map<SaveResponse>(SaveResponse::Success)
            .doOnError { error -> log.error("При сохранении шаблона произошла ошибка", error) }
            .onErrorResume { SaveResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    override fun delete(id: UUID): Mono<Unit> =
        Mono.fromCallable { repository.deleteById(id) }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun findAllLikeName(name: String): Mono<FindAllResponse> =
        Mono.fromCallable { repository.findAllLikeName(name) }
            .map { list -> list.map(ComponentTemplateFromDatabase::emerge) }
            .map<FindAllResponse>(FindAllResponse::Success)
            .doOnError { error -> log.error("При получении шаблонов по имени произошла ошибка", error) }
            .onErrorResume { FindAllResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun existsById(id: UUID): Mono<Boolean> =
        Mono.fromCallable { repository.existsById(id) }
            .doOnError { error -> log.error("При проверке наличия шаблона произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())
}