package ru.hits.bdui.common.models.admin.entity.components

sealed interface CompositeEntity : ComponentEntity {
    val children: List<ComponentEntity>
}

data class RowEntity(
    override val children: List<ComponentEntity>,
    override val base: ComponentBaseEntityProperties
) : CompositeEntity {
    override val type: String = "row"
}

data class BoxEntity(
    override val children: List<ComponentEntity>,
    override val base: ComponentBaseEntityProperties
) : CompositeEntity {
    override val type: String = "box"
}

data class ColumnEntity(
    override val children: List<ComponentEntity>,
    override val base: ComponentBaseEntityProperties
) : CompositeEntity {
    override val type: String = "column"
}