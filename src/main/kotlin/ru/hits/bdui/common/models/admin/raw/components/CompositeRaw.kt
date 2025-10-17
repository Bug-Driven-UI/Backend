package ru.hits.bdui.common.models.admin.raw.components

import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalArrangementRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalOrVerticalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.VerticalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.VerticalArrangementRaw

sealed interface CompositeRaw : ComponentRaw {
    val children: List<ComponentRaw>
}

data class RowRaw(
    override val children: List<ComponentRaw>,
    override val base: ComponentBaseRawProperties,
    val horizontalArrangement: HorizontalArrangementRaw?,
    val verticalAlignment: VerticalAlignmentRaw?
) : CompositeRaw {
    override val type: String = "row"
}

data class BoxRaw(
    override val children: List<ComponentRaw>,
    override val base: ComponentBaseRawProperties,
    val contentAlignment: HorizontalOrVerticalAlignmentRaw?
) : CompositeRaw {
    override val type: String = "box"
}

data class ColumnRaw(
    override val children: List<ComponentRaw>,
    override val base: ComponentBaseRawProperties,
    val verticalArrangement: VerticalArrangementRaw?,
    val horizontalAlignment: HorizontalAlignmentRaw?
) : CompositeRaw {
    override val type: String = "column"
}