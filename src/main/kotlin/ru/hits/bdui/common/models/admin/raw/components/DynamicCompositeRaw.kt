package ru.hits.bdui.common.models.admin.raw.components

import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalArrangementRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.VerticalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.VerticalArrangementRaw

sealed interface DynamicCompositeRaw : ComponentRaw {
    val itemsData: String
    val itemAlias: String
    val itemTemplateName: String
}

/**
 * Динамически заполняемая колонка
 */
data class DynamicColumnRaw(
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplateName: String,
    override val base: ComponentBaseRawProperties,
    val verticalArrangement: VerticalArrangementRaw?,
    val horizontalAlignment: HorizontalAlignmentRaw?
) : DynamicCompositeRaw {
    override val type: String = "dynamicColumn"
}

/**
 * Динамически заполняемая строка
 */
data class DynamicRowRaw(
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplateName: String,
    override val base: ComponentBaseRawProperties,
    val horizontalArrangement: HorizontalArrangementRaw?,
    val verticalAlignment: VerticalAlignmentRaw?
) : DynamicCompositeRaw {
    override val type: String = "dynamicRow"
}