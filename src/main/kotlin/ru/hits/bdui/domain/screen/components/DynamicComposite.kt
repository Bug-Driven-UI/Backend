package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.engine.ComponentEvaluator
import ru.hits.bdui.domain.template.ComponentTemplate
import ru.hits.bdui.engine.screen.component.DynamicCompositeEvaluator

sealed interface DynamicComposite : Component {
    val itemsData: String
    val itemAlias: String
    val itemTemplate: ComponentTemplate
    override val evaluator: ComponentEvaluator
        get() = DynamicCompositeEvaluator()
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
    private val basePropertiesSet: ComponentPropertiesSet,
) : DynamicComposite, BaseComponentProperties by basePropertiesSet {
    override val type: String = "dynamicColumn"

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
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
    private val basePropertiesSet: ComponentPropertiesSet,
) : DynamicComposite, BaseComponentProperties by basePropertiesSet {
    override val type: String = "dynamicRow"

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}