package ru.hits.bdui.common.models.admin.raw.components

sealed interface CompositeRaw : ComponentRaw {
    val children: List<ComponentRaw>
}

data class RowRaw(
    override val children: List<ComponentRaw>,
    override val base: ComponentBaseRawProperties,
) : CompositeRaw {
    override val type: String = "row"
}

data class BoxRaw(
    override val children: List<ComponentRaw>,
    override val base: ComponentBaseRawProperties,
) : CompositeRaw {
    override val type: String = "box"
}

data class ColumnRaw(
    override val children: List<ComponentRaw>,
    override val base: ComponentBaseRawProperties,
) : CompositeRaw {
    override val type: String = "column"
}