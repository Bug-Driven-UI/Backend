package ru.hits.bdui.admin.externalapi

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import ru.hits.bdui.admin.externalapi.database.emerge.emerge
import ru.hits.bdui.admin.externalapi.database.entity.ApiRepresentationEntity
import ru.hits.bdui.admin.externalapi.database.repository.ApiRepresentationRepository
import ru.hits.bdui.common.exceptions.notFound
import ru.hits.bdui.domain.api.ApiRepresentation
import ru.hits.bdui.domain.api.ApiRepresentationFromDatabase
import ru.hits.bdui.domain.api.ShortApiRepresentation
import java.util.*

interface ApiRepresentationStorageService {

    fun getApiRepresentation(id: UUID): Mono<ApiRepresentationFromDatabase>

    fun getApiRepresentations(ids: Set<UUID>): Mono<List<ApiRepresentationFromDatabase>>

    fun createApiRepresentation(apiRepresentation: ApiRepresentation): Mono<ApiRepresentationFromDatabase>

    fun deleteApiRepresentation(id: UUID): Mono<Unit>

    fun updateApiRepresentation(id: UUID, apiRepresentation: ApiRepresentation): Mono<ApiRepresentationFromDatabase>

    fun queryApiNames(query: String): Mono<List<ShortApiRepresentation>>
}

@Service
class ApiRepresentationStorageServiceImpl(
    private val repository: ApiRepresentationRepository
) : ApiRepresentationStorageService {

    override fun getApiRepresentation(id: UUID): Mono<ApiRepresentationFromDatabase> =
        Mono.fromCallable { repository.findById(id) }
            .map { result ->
                result.orElseThrow { notFound<ApiRepresentationFromDatabase>(id) }
            }
            .map { result -> ApiRepresentationFromDatabase.emerge(result) }
            .subscribeOn(Schedulers.boundedElastic())

    override fun getApiRepresentations(ids: Set<UUID>): Mono<List<ApiRepresentationFromDatabase>> =
        Mono.fromCallable { repository.findAllById(ids) }
            .map { entities -> entities.map(ApiRepresentationFromDatabase::emerge) }
            .subscribeOn(Schedulers.boundedElastic())

    override fun createApiRepresentation(apiRepresentation: ApiRepresentation): Mono<ApiRepresentationFromDatabase> =
        Mono.fromCallable {
            val entity = ApiRepresentationEntity.emerge(apiRepresentation)
            repository.save(entity)
        }
            .map(ApiRepresentationFromDatabase::emerge)
            .subscribeOn(Schedulers.boundedElastic())

    override fun deleteApiRepresentation(id: UUID): Mono<Unit> =
        Mono.fromCallable { repository.deleteById(id) }
            .subscribeOn(Schedulers.boundedElastic())

    override fun updateApiRepresentation(id: UUID, apiRepresentation: ApiRepresentation): Mono<ApiRepresentationFromDatabase> =
        Mono.fromCallable {
            val existingEntity = repository.findById(id)
                .orElseThrow { notFound<ApiRepresentationFromDatabase>(id) }
            val updatedEntity = ApiRepresentationEntity.emerge(
                ApiRepresentationFromDatabase(
                    id = id,
                    api = apiRepresentation,
                    createdAtTimestampMs = existingEntity.createdAt,
                    lastModifiedTimestampMs = System.currentTimeMillis()
                )
            )
            repository.save(updatedEntity)
        }
            .map(ApiRepresentationFromDatabase::emerge)
            .subscribeOn(Schedulers.boundedElastic())

    override fun queryApiNames(query: String): Mono<List<ShortApiRepresentation>> =
        Mono.fromCallable { repository.findByNameOrDescriptionContaining(query) }
            .map { entities ->
                entities.map { entity ->
                    ShortApiRepresentation(
                        id = entity.id,
                        name = entity.name,
                        description = entity.description
                    )
                }
            }
            .subscribeOn(Schedulers.boundedElastic())
}
