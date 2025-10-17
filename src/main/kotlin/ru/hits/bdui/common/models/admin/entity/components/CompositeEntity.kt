package ru.hits.bdui.common.models.admin.entity.components

import ru.hits.bdui.common.models.admin.entity.components.additional.HorizontalAlignmentEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.HorizontalArrangementEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.HorizontalOrVerticalAlignmentEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.VerticalAlignmentEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.VerticalArrangementEntity

sealed interface CompositeEntity : ComponentEntity {
    val children: List<ComponentEntity>
}

data class RowEntity(
    override val children: List<ComponentEntity>,
    override val base: ComponentBaseEntityProperties,
    val horizontalArrangement: HorizontalArrangementEntity?,
    val verticalAlignment: VerticalAlignmentEntity?
) : CompositeEntity {
    override val type: String = "row"
}

data class BoxEntity(
    override val children: List<ComponentEntity>,
    override val base: ComponentBaseEntityProperties,
    val contentAlignment: HorizontalOrVerticalAlignmentEntity?
) : CompositeEntity {
    override val type: String = "box"
}

data class ColumnEntity(
    override val children: List<ComponentEntity>,
    override val base: ComponentBaseEntityProperties,
    val verticalArrangement: VerticalArrangementEntity?,
    val horizontalAlignment: HorizontalAlignmentEntity?
) : CompositeEntity {
    override val type: String = "column"
}