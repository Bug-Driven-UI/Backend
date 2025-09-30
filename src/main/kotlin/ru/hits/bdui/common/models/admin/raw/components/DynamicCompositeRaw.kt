package ru.hits.bdui.common.models.admin.raw.components

sealed interface DynamicCompositeRaw : ComponentRaw {
    val itemsData: String
    val itemAlias: String
    val itemTemplateName: String
}

/**
 * Динамически заполняемая колонка
 */
data class DynamicColumnRaw(
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplateName: String,
    override val base: ComponentBaseRawProperties,
) : DynamicCompositeRaw {
    override val type: String = "dynamicColumn"
}

/**
 * Динамически заполняемая строка
 */
data class DynamicRowRaw(
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplateName: String,
    override val base: ComponentBaseRawProperties,
) : DynamicCompositeRaw {
    override val type: String = "dynamicRow"
}