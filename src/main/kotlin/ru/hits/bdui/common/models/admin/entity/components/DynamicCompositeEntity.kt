package ru.hits.bdui.common.models.admin.entity.components

import ru.hits.bdui.common.models.admin.entity.components.additional.HorizontalAlignmentEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.HorizontalArrangementEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.VerticalAlignmentEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.VerticalArrangementEntity

sealed interface DynamicCompositeEntity : ComponentEntity {
    val itemsData: String
    val itemAlias: String
    val itemTemplate: ComponentTemplateEntity
}

/**
 * Динамически заполняемая колонка
 */
data class DynamicColumnEntity(
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplate: ComponentTemplateEntity,
    override val base: ComponentBaseEntityProperties,
    val verticalArrangement: VerticalArrangementEntity?,
    val horizontalAlignment: HorizontalAlignmentEntity?
) : DynamicCompositeEntity

/**
 * Динамически заполняемая строка
 */
data class DynamicRowEntity(
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplate: ComponentTemplateEntity,
    override val base: ComponentBaseEntityProperties,
    val horizontalArrangement: HorizontalArrangementEntity?,
    val verticalAlignment: VerticalAlignmentEntity?,
    val isScrollable: Boolean?,
) : DynamicCompositeEntity