package ru.hits.bdui.common.models.client.raw.components

import ru.hits.bdui.common.models.client.raw.components.additional.RenderedHorizontalAlignmentRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedHorizontalAndVerticalAlignmentRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedHorizontalArrangementRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedVerticalAlignmentRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedVerticalArrangementRaw

sealed interface CompositeRawRendered : RenderedComponentRaw {
    val children: List<RenderedComponentRaw>
}

data class RowRawRendered(
    override val children: List<RenderedComponentRaw>,
    override val base: RenderedComponentBaseRawProperties,
    val horizontalArrangement: RenderedHorizontalArrangementRaw?,
    val verticalAlignment: RenderedVerticalAlignmentRaw?
) : CompositeRawRendered

data class BoxRawRendered(
    override val children: List<RenderedComponentRaw>,
    override val base: RenderedComponentBaseRawProperties,
    val contentAlignment: RenderedHorizontalAndVerticalAlignmentRaw?
) : CompositeRawRendered

data class ColumnRawRendered(
    override val children: List<RenderedComponentRaw>,
    override val base: RenderedComponentBaseRawProperties,
    val verticalArrangement: RenderedVerticalArrangementRaw?,
    val horizontalAlignment: RenderedHorizontalAlignmentRaw?
) : CompositeRawRendered
