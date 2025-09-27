package ru.hits.bdui.textStyles.controller.raw.delete

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.raw.ErrorContentRaw

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = TextStyleDeleteResponseSuccessRaw::class, name = "success"),
    JsonSubTypes.Type(value = TextStyleDeleteResponseErrorRaw::class, name = "error")
)
sealed interface TextStyleDeleteResponseRaw

data class TextStyleDeleteResponseSuccessRaw(
    val message: String,
) : TextStyleDeleteResponseRaw

data class TextStyleDeleteResponseErrorRaw(
    val errors: List<ErrorContentRaw>,
) : TextStyleDeleteResponseRaw