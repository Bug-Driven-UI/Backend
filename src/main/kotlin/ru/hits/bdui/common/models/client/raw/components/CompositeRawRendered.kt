package ru.hits.bdui.common.models.client.raw.components

sealed interface CompositeRawRendered : RenderedComponentRaw {
    val children: List<RenderedComponentRaw>
}

data class RowRawRendered(
    override val children: List<RenderedComponentRaw>,
    override val base: RenderedComponentBaseRawProperties,
) : CompositeRawRendered

data class BoxRawRendered(
    override val children: List<RenderedComponentRaw>,
    override val base: RenderedComponentBaseRawProperties,
) : CompositeRawRendered

data class ColumnRawRendered(
    override val children: List<RenderedComponentRaw>,
    override val base: RenderedComponentBaseRawProperties,
) : CompositeRawRendered
