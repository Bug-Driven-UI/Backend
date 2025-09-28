package ru.hits.bdui.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.hits.bdui.entity.api.ApiRepresentationEntity
import ru.hits.bdui.model.api.*
import ru.hits.bdui.repository.ApiRepresentationRepository

@Service
class ApiRepresentationStorageServiceImpl(
    private val repository: ApiRepresentationRepository
) : ApiRepresentationStorageService {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun getApiRepresentation(getModel: ApiRepresentationGetModel): ApiRepresentationModel {
        val entity = repository.findById(getModel.apiId)
            .orElseThrow { IllegalArgumentException("API Representation with ID ${getModel.apiId} not found") }

        return entity.toModel()
    }

    override fun getApiRepresentations(getModel: ApiRepresentationsGetModel): List<ApiRepresentationModel> {
        val entities = repository.findAllById(getModel.ids)
        if (entities.size != getModel.ids.size) {
            val foundIds = entities.mapNotNull { it.id }
            val notFoundIds = getModel.ids.filterNot { it in foundIds }
            log.warn("API Representations with IDs $notFoundIds not found")
        }
        return entities.map { it.toModel() }
    }

    override fun createApiRepresentation(createModel: ApiRepresentationCreateModel): ApiRepresentationModel {
        val entity = ApiRepresentationEntity(
            name = createModel.name,
            description = createModel.description,
            params = createModel.params,
            endpoints = createModel.endpoints,
            schema = createModel.schema,
            mappingScript = createModel.mappingScript,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
        )

        val savedEntity = repository.save(entity)
        return savedEntity.toModel()
    }

    override fun deleteApiRepresentation(deleteModel: ApiRepresentationDeleteModel) {
        if (!repository.existsById(deleteModel.apiId)) {
            throw IllegalArgumentException("API Representation with ID ${deleteModel.apiId} not found")
        }
        repository.deleteById(deleteModel.apiId)
    }

    override fun updateApiRepresentation(updateModel: ApiRepresentationUpdateModel): ApiRepresentationModel {
        val existingEntity = repository.findById(updateModel.apiId)
            .orElseThrow { IllegalArgumentException("API Representation with ID ${updateModel.apiId} not found") }

        val updatedEntity = existingEntity.copy(
            name = updateModel.api.name,
            description = updateModel.api.description,
            params = updateModel.api.params,
            endpoints = updateModel.api.endpoints,
            schema = updateModel.api.schema,
            mappingScript = updateModel.api.mappingScript,
            updatedAt = System.currentTimeMillis(),
        )
        val savedEntity = repository.save(updatedEntity)
        return savedEntity.toModel()
    }

    override fun queryApiNames(queryModel: ApiRepresentationQueryModel): ApiRepresentationShortList {
        val entities = repository.findByNameOrDescriptionContaining(queryModel.query)
        val shortModels = entities.map { entity ->
            ApiRepresentationShortModel(
                id = requireNotNull(entity.id),
                name = entity.name,
                description = entity.description
            )
        }
        return ApiRepresentationShortList(apiNames = shortModels)
    }

    private fun ApiRepresentationEntity.toModel(): ApiRepresentationModel {
        return ApiRepresentationModel(
            id = requireNotNull(this.id),
            name = this.name,
            description = this.description,
            params = this.params,
            endpoints = this.endpoints,
            schema = this.schema,
            mappingScript = this.mappingScript,
            createdAtTimestampMs = this.createdAt,
            lastModifiedAtTimestampMs = this.updatedAt,
        )
    }
}