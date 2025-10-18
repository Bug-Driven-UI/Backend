package ru.hits.bdui.common.models.admin.raw.components

import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalAndVerticalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalArrangementRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.VerticalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.VerticalArrangementRaw

sealed interface CompositeRaw : ComponentRaw {
    val children: List<ComponentRaw>
}

data class RowRaw(
    override val children: List<ComponentRaw>,
    override val base: ComponentBaseRawProperties,
    val horizontalArrangement: HorizontalArrangementRaw?,
    val verticalAlignment: VerticalAlignmentRaw?,
    val isScrollable: Boolean?,
) : CompositeRaw

data class BoxRaw(
    override val children: List<ComponentRaw>,
    override val base: ComponentBaseRawProperties,
    val contentAlignment: HorizontalAndVerticalAlignmentRaw?
) : CompositeRaw

data class ColumnRaw(
    override val children: List<ComponentRaw>,
    override val base: ComponentBaseRawProperties,
    val verticalArrangement: VerticalArrangementRaw?,
    val horizontalAlignment: HorizontalAlignmentRaw?
) : CompositeRaw