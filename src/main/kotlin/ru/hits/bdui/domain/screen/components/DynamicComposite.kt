package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.screen.components.properties.Insets
import ru.hits.bdui.domain.screen.components.properties.Size
import ru.hits.bdui.domain.screen.interactions.Interaction
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
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
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
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : DynamicComposite {
    override val type: String = "dynamicRow"
}