package ru.hits.bdui.common.models.client.raw.components

sealed interface CompositeRawRendered : RenderedComponentRaw {
    val children: List<RenderedComponentRaw>
}

data class RowRawRendered(
    override val children: List<RenderedComponentRaw>,
    override val base: RenderedComponentBaseRawProperties,
) : CompositeRawRendered {
    override val type: String = "row"
}

data class BoxRawRendered(
    override val children: List<RenderedComponentRaw>,
    override val base: RenderedComponentBaseRawProperties,
) : CompositeRawRendered {
    override val type: String = "box"
}

data class ColumnRawRendered(
    override val children: List<RenderedComponentRaw>,
    override val base: RenderedComponentBaseRawProperties,
) : CompositeRawRendered {
    override val type: String = "column"
}
