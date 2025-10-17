package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.screen.components.additional.HorizontalArrangement
import ru.hits.bdui.domain.screen.components.additional.HorizontalOrVerticalAlignment
import ru.hits.bdui.domain.screen.components.additional.VerticalArrangement
import ru.hits.bdui.domain.template.ComponentTemplate

sealed interface DynamicComposite : Component {
    val itemsData: String
    val itemAlias: String
    val itemTemplate: ComponentTemplate
}

/**
 * Динамически заполняемая колонка
 *
 * Преобразуется в column
 */
data class DynamicColumn(
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplate: ComponentTemplate,
    override val base: ComponentBaseProperties,
    val verticalArrangement: VerticalArrangement?,
    val horizontalAlignment: HorizontalOrVerticalAlignment.HorizontalAlignment?
) : DynamicComposite {
    override val type: String = "dynamicColumn"
}

/**
 * Динамически заполняемая строка
 *
 * Преобразуется в row
 */
data class DynamicRow(
    override val itemsData: String,
    override val itemAlias: String,
    override val itemTemplate: ComponentTemplate,
    override val base: ComponentBaseProperties,
    val horizontalArrangement: HorizontalArrangement?,
    val verticalAlignment: HorizontalOrVerticalAlignment.VerticalAlignment?
) : DynamicComposite {
    override val type: String = "dynamicRow"
}