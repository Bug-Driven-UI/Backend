package ru.hits.bdui.common.models.admin.entity.components

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.admin.entity.components.additional.BorderEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.ShapeEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.InsetsEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.SizeEntity
import ru.hits.bdui.common.models.admin.entity.interactions.InteractionEntity
import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = TextEntity::class, name = "text"),
    JsonSubTypes.Type(value = InputEntity::class, name = "textField"),
    JsonSubTypes.Type(value = ImageEntity::class, name = "image"),
    JsonSubTypes.Type(value = SpacerEntity::class, name = "spacer"),
    JsonSubTypes.Type(value = ProgressBarEntity::class, name = "progressBar"),
    JsonSubTypes.Type(value = SwitchEntity::class, name = "switch"),
    JsonSubTypes.Type(value = ButtonEntity::class, name = "button"),
    JsonSubTypes.Type(value = DynamicColumnEntity::class, name = "dynamicColumn"),
    JsonSubTypes.Type(value = DynamicRowEntity::class, name = "dynamicRow"),
    JsonSubTypes.Type(value = StatefulComponentEntity::class, name = "stateful"),
    JsonSubTypes.Type(value = ColumnEntity::class, name = "column"),
    JsonSubTypes.Type(value = RowEntity::class, name = "row"),
    JsonSubTypes.Type(value = BoxEntity::class, name = "box"),
)
sealed interface ComponentEntity {
    val id: String
    val type: String
    val interactions: List<InteractionEntity>
    val margins: InsetsEntity?
    val paddings: InsetsEntity?
    val width: SizeEntity
    val height: SizeEntity
    val backgroundColor: ColorStyleEntity?
    val border: BorderEntity?
    val shape: ShapeEntity?

    companion object
}
