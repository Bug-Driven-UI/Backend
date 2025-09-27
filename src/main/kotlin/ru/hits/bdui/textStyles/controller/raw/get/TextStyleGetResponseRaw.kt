package ru.hits.bdui.textStyles.controller.raw.get

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.raw.ErrorContentRaw
import ru.hits.bdui.textStyles.controller.raw.TextStyleRaw

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = TextStyleGetResponseSuccessRaw::class, name = "success"),
    JsonSubTypes.Type(value = TextStyleGetResponseErrorRaw::class, name = "error")
)
sealed interface TextStyleGetResponseRaw

data class TextStyleGetResponseSuccessRaw(
    val textStyle: TextStyleRaw
) : TextStyleGetResponseRaw

data class TextStyleGetResponseErrorRaw(
    val errors: List<ErrorContentRaw>,
) : TextStyleGetResponseRaw