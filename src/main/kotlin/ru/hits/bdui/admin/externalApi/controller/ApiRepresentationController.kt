package ru.hits.bdui.admin.externalApi.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.externalApi.ApiRepresentationStorageService
import ru.hits.bdui.admin.externalApi.controller.raw.ApiRepresentationCreateRequestRaw
import ru.hits.bdui.admin.externalApi.controller.raw.ApiRepresentationDeleteRequestRaw
import ru.hits.bdui.admin.externalApi.controller.raw.ApiRepresentationGetRequestRaw
import ru.hits.bdui.admin.externalApi.controller.raw.ApiRepresentationQueryRequestRaw
import ru.hits.bdui.admin.externalApi.controller.raw.ApiRepresentationRawMapper
import ru.hits.bdui.admin.externalApi.controller.raw.ApiRepresentationResponseRaw
import ru.hits.bdui.admin.externalApi.controller.raw.ApiRepresentationShortListResponseRaw
import ru.hits.bdui.admin.externalApi.controller.raw.ApiRepresentationUpdateRequestRaw
import ru.hits.bdui.common.models.api.ApiResponse
import ru.hits.bdui.common.models.api.DataModel
import ru.hits.bdui.common.models.api.DeleteResponseRaw

@RestController
@RequestMapping("/v1/api")
class ApiRepresentationController(
    private val apiRepresentationService: ApiRepresentationStorageService
) {

    @PostMapping("/save")
    fun saveApiRepresentation(
        @RequestBody saveModel: ApiRepresentationCreateRequestRaw,
    ): Mono<ApiResponse<ApiRepresentationResponseRaw>> =
        Mono.fromCallable { ApiRepresentationRawMapper.toDomain(saveModel) }
            .flatMap { domainModel -> apiRepresentationService.createApiRepresentation(domainModel) }
            .map(ApiRepresentationRawMapper::fromDomain)
            .map(ApiResponse.Companion::success)

    @PutMapping("/update")
    fun updateApiRepresentation(
        @RequestBody updateModel: DataModel<ApiRepresentationUpdateRequestRaw>,
    ): Mono<ApiResponse<ApiRepresentationResponseRaw>> =
        Mono.fromCallable { ApiRepresentationRawMapper.toDomain(updateModel.data) }
            .flatMap { domainModel ->
                apiRepresentationService.updateApiRepresentation(updateModel.data.apiId, domainModel)
            }
            .map(ApiRepresentationRawMapper::fromDomain)
            .map(ApiResponse.Companion::success)

    @DeleteMapping("/delete")
    fun deleteApiRepresentation(
        @RequestBody deleteModel: DataModel<ApiRepresentationDeleteRequestRaw>,
    ): Mono<ApiResponse<DeleteResponseRaw>> =
        Mono.fromCallable { deleteModel.data.apiId }
            .flatMap { apiId -> apiRepresentationService.deleteApiRepresentation(apiId) }
            .map { DeleteResponseRaw("Api representation with id $${deleteModel.data.apiId} deleted successfully") }
            .map(ApiResponse.Companion::success)

    @PostMapping("/get")
    fun getApiRepresentation(
        @RequestBody getModel: DataModel<ApiRepresentationGetRequestRaw>,
    ): Mono<ApiResponse<ApiRepresentationResponseRaw>> =
        Mono.fromCallable { getModel.data.apiId }
            .flatMap { apiId -> apiRepresentationService.getApiRepresentation(apiId) }
            .map(ApiRepresentationRawMapper::fromDomain)
            .map(ApiResponse.Companion::success)

    @PostMapping("/getByName")
    fun queryApiNames(
        @RequestBody queryModel: DataModel<ApiRepresentationQueryRequestRaw>,
    ): Mono<ApiResponse<ApiRepresentationShortListResponseRaw>> =
        Mono.fromCallable { queryModel.data.query }
            .flatMap { query -> apiRepresentationService.queryApiNames(query) }
            .map { list -> list.map(ApiRepresentationRawMapper::fromDomain) }
            .map { ApiRepresentationShortListResponseRaw(it) }
            .map(ApiResponse.Companion::success)
}