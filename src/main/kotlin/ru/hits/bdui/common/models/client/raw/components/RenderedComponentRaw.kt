package ru.hits.bdui.common.models.client.raw.components

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonUnwrapped
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedBorderRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedShapeRaw
import ru.hits.bdui.common.models.client.raw.components.properties.RenderedInsetsRaw
import ru.hits.bdui.common.models.client.raw.components.properties.RenderedSizeRaw
import ru.hits.bdui.common.models.client.raw.interactions.RenderedInteractionRaw
import ru.hits.bdui.common.models.client.raw.styles.color.RenderedColorStyleRaw

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = TextRawRendered::class, name = "text"),
    JsonSubTypes.Type(value = InputRawRendered::class, name = "input"),
    JsonSubTypes.Type(value = ImageRawRendered::class, name = "image"),
    JsonSubTypes.Type(value = SpacerRawRendered::class, name = "spacer"),
    JsonSubTypes.Type(value = ProgressBarRawRendered::class, name = "progressBar"),
    JsonSubTypes.Type(value = SwitchRawRendered::class, name = "switch"),
    JsonSubTypes.Type(value = ButtonRawRendered::class, name = "button"),
    JsonSubTypes.Type(value = ColumnRawRendered::class, name = "column"),
    JsonSubTypes.Type(value = RowRawRendered::class, name = "row"),
    JsonSubTypes.Type(value = BoxRawRendered::class, name = "box"),
)
sealed interface RenderedComponentRaw {
    val type: String

    @get:JsonUnwrapped
    val base: RenderedComponentBaseRawProperties

    companion object
}

/**
 * Базовый набор свойств для каждого компонента
 */
data class RenderedComponentBaseRawProperties(
    val id: String,
    val hash: String,
    val interactions: List<RenderedInteractionRaw>,
    val margins: RenderedInsetsRaw?,
    val paddings: RenderedInsetsRaw?,
    val width: RenderedSizeRaw,
    val height: RenderedSizeRaw,
    val backgroundColor: RenderedColorStyleRaw?,
    val border: RenderedBorderRaw?,
    val shape: RenderedShapeRaw?,
)
