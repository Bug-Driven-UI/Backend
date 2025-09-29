package ru.hits.bdui.common.models.admin.raw.components

import ru.hits.bdui.common.models.admin.raw.components.additional.BorderRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.ShapeRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.InsetsRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.SizeRaw
import ru.hits.bdui.common.models.admin.raw.interactions.InteractionRaw
import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw

sealed interface CompositeRaw : ComponentRaw {
    val children: List<ComponentRaw>
}

data class RowRaw(
    override val id: String,
    override val children: List<ComponentRaw>,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : CompositeRaw {
    override val type: String = "row"
}

data class BoxRaw(
    override val id: String,
    override val children: List<ComponentRaw>,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : CompositeRaw {
    override val type: String = "box"
}

data class ColumnRaw(
    override val id: String,
    override val children: List<ComponentRaw>,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : CompositeRaw {
    override val type: String = "column"
}