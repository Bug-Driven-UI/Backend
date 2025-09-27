package ru.hits.bdui.textStyles.controller.raw.update

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.raw.ErrorContentRaw
import ru.hits.bdui.textStyles.controller.raw.TextStyleRaw

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = TextStyleUpdateResponseSuccessRaw::class, name = "success"),
    JsonSubTypes.Type(value = TextStyleUpdateResponseErrorRaw::class, name = "error")
)
sealed interface TextStyleUpdateResponseRaw

data class TextStyleUpdateResponseSuccessRaw(
    val textStyle: TextStyleRaw
) : TextStyleUpdateResponseRaw

data class TextStyleUpdateResponseErrorRaw(
    val errors: List<ErrorContentRaw>,
) : TextStyleUpdateResponseRaw