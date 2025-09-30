package ru.hits.bdui.common.models.admin.raw.components

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonUnwrapped
import ru.hits.bdui.common.models.admin.raw.components.additional.BorderRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.ShapeRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.InsetsRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.SizeRaw
import ru.hits.bdui.common.models.admin.raw.interactions.InteractionRaw
import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = TextRaw::class, name = "text"),
    JsonSubTypes.Type(value = InputRaw::class, name = "textField"),
    JsonSubTypes.Type(value = ImageRaw::class, name = "image"),
    JsonSubTypes.Type(value = SpacerRaw::class, name = "spacer"),
    JsonSubTypes.Type(value = ProgressBarRaw::class, name = "progressBar"),
    JsonSubTypes.Type(value = SwitchRaw::class, name = "switch"),
    JsonSubTypes.Type(value = ButtonRaw::class, name = "button"),
    JsonSubTypes.Type(value = DynamicColumnRaw::class, name = "dynamicColumn"),
    JsonSubTypes.Type(value = DynamicRowRaw::class, name = "dynamicRow"),
    JsonSubTypes.Type(value = StatefulComponentRaw::class, name = "stateful"),
    JsonSubTypes.Type(value = ColumnRaw::class, name = "column"),
    JsonSubTypes.Type(value = RowRaw::class, name = "row"),
    JsonSubTypes.Type(value = BoxRaw::class, name = "box"),
)
sealed interface ComponentRaw {
    val type: String

    @get:JsonUnwrapped
    val base: ComponentBaseRawProperties

    companion object
}

/**
 * Базовый набор свойств для каждого компонента
 */
data class ComponentBaseRawProperties(
    val id: String,
    val interactions: List<InteractionRaw>,
    val margins: InsetsRaw?,
    val paddings: InsetsRaw?,
    val width: SizeRaw,
    val height: SizeRaw,
    val backgroundColor: ColorStyleRaw?,
    val border: BorderRaw?,
    val shape: ShapeRaw?,
)
