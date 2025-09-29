package ru.hits.bdui.common.models.admin.entity.components

import ru.hits.bdui.common.models.admin.entity.components.additional.BorderEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.ShapeEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.InsetsEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.SizeEntity
import ru.hits.bdui.common.models.admin.entity.interactions.InteractionEntity
import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity

sealed interface DynamicCompositeEntity : ComponentEntity {
    val itemsData: String
    val itemAlias: String
    val itemTemplate: ComponentTemplateEntity
}

/**
 * Динамически заполняемая колонка
 */
data class DynamicColumnEntity(
    override val id: String,
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplate: ComponentTemplateEntity,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : DynamicCompositeEntity {
    override val type: String = "dynamicColumn"
}

/**
 * Динамически заполняемая строка
 */
data class DynamicRowEntity(
    override val id: String,
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplate: ComponentTemplateEntity,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : DynamicCompositeEntity {
    override val type: String = "dynamicRow"
}