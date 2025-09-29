package ru.hits.bdui.common.models.admin.raw.components

import ru.hits.bdui.common.models.admin.raw.components.additional.BorderRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.ShapeRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.InsetsRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.SizeRaw
import ru.hits.bdui.common.models.admin.raw.interactions.InteractionRaw
import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw

sealed interface DynamicCompositeRaw : ComponentRaw {
    val itemsData: String
    val itemAlias: String
    val itemTemplateName: String
}

/**
 * Динамически заполняемая колонка
 */
data class DynamicColumnRaw(
    override val id: String,
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplateName: String,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : DynamicCompositeRaw {
    override val type: String = "dynamicColumn"
}

/**
 * Динамически заполняемая строка
 */
data class DynamicRowRaw(
    override val id: String,
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplateName: String,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : DynamicCompositeRaw {
    override val type: String = "dynamicRow"
}