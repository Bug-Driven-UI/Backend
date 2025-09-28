package ru.hits.bdui.controller

import org.springframework.web.bind.annotation.*
import ru.hits.bdui.model.api.*
import ru.hits.bdui.model.common.*
import ru.hits.bdui.service.ApiRepresentationStorageService

@RestController
@RequestMapping("/v1/api")
class ApiRepresentationController(
    private val apiRepresentationService: ApiRepresentationStorageService,
) {

    @PostMapping("/save")
    fun saveApiRepresentation(
        @RequestBody saveModel: ApiRepresentationCreateModel,
    ) = successResponseBuilder(successDataName = "api") {
        apiRepresentationService.createApiRepresentation(saveModel)
    }.defaultErrorResponseBuilder().getResponse()

    @PutMapping("/update")
    fun updateApiRepresentation(
        @RequestBody updateModel: DataModel<ApiRepresentationUpdateModel>,
    ) = successResponseBuilder(successDataName = "api") {
        apiRepresentationService.updateApiRepresentation(updateModel.data)
    }.defaultErrorResponseBuilder().getResponse()

    @DeleteMapping("/delete")
    fun deleteApiRepresentation(
        @RequestBody deleteModel: DataModel<ApiRepresentationDeleteModel>,
    ) = successResponseBuilder(successDataName = "message") {
        apiRepresentationService.deleteApiRepresentation(deleteModel.data)
        "Api representation with id ${deleteModel.data.apiId} deleted successfully"
    }.defaultErrorResponseBuilder().getResponse()

    @PostMapping("/get")
    fun getApiRepresentation(
        @RequestBody getModel: DataModel<ApiRepresentationGetModel>,
    ) = successResponseBuilder(successDataName = "api") {
        apiRepresentationService.getApiRepresentation(getModel.data)
    }.defaultErrorResponseBuilder().getResponse()

    @PostMapping("/getByName")
    fun queryApiNames(
        @RequestBody queryModel: DataModel<ApiRepresentationQueryModel>,
    ) = successResponseBuilder {
        apiRepresentationService.queryApiNames(queryModel.data)
    }.defaultErrorResponseBuilder().getResponse()
}