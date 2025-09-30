package ru.hits.bdui.common.models.admin.entity.components

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
    override val base: ComponentBaseEntityProperties
) : DynamicCompositeEntity {
    override val type: String = "dynamicColumn"
}

/**
 * Динамически заполняемая строка
 */
data class DynamicRowEntity(
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplate: ComponentTemplateEntity,
    override val base: ComponentBaseEntityProperties
) : DynamicCompositeEntity {
    override val type: String = "dynamicRow"
}