package ru.hits.bdui.textStyles.controller.raw.save

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.raw.ErrorContentRaw
import ru.hits.bdui.textStyles.controller.raw.TextStyleRaw

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = TextStyleSaveResponseSuccessRaw::class, name = "success"),
    JsonSubTypes.Type(value = TextStyleSaveResponseErrorRaw::class, name = "error")
)
sealed interface TextStyleSaveResponseRaw

data class TextStyleSaveResponseSuccessRaw(
    val textStyle: TextStyleRaw
) : TextStyleSaveResponseRaw

data class TextStyleSaveResponseErrorRaw(
    val errors: List<ErrorContentRaw>,
) : TextStyleSaveResponseRaw