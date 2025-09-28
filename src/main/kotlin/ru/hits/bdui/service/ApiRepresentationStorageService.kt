package ru.hits.bdui.service

import ru.hits.bdui.model.api.*

interface ApiRepresentationStorageService {

    fun getApiRepresentation(getModel: ApiRepresentationGetModel): ApiRepresentationModel

    fun getApiRepresentations(getModel: ApiRepresentationsGetModel): List<ApiRepresentationModel>

    fun createApiRepresentation(createModel: ApiRepresentationCreateModel): ApiRepresentationModel

    fun deleteApiRepresentation(deleteModel: ApiRepresentationDeleteModel)

    fun updateApiRepresentation(updateModel: ApiRepresentationUpdateModel): ApiRepresentationModel

    fun queryApiNames(queryModel: ApiRepresentationQueryModel): ApiRepresentationShortList
}
