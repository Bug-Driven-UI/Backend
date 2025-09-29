package ru.hits.bdui.common.models.admin.entity.components

import ru.hits.bdui.common.models.admin.entity.components.additional.BorderEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.ShapeEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.InsetsEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.SizeEntity
import ru.hits.bdui.common.models.admin.entity.interactions.InteractionEntity
import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity

sealed interface CompositeEntity : ComponentEntity {
    val children: List<ComponentEntity>
}

data class RowEntity(
    override val id: String,
    override val children: List<ComponentEntity>,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : CompositeEntity {
    override val type: String = "row"
}

data class BoxEntity(
    override val id: String,
    override val children: List<ComponentEntity>,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : CompositeEntity {
    override val type: String = "box"
}

data class ColumnEntity(
    override val id: String,
    override val children: List<ComponentEntity>,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : CompositeEntity {
    override val type: String = "column"
}