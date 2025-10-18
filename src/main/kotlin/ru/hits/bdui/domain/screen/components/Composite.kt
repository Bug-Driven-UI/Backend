package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.screen.components.additional.HorizontalAlignment
import ru.hits.bdui.domain.screen.components.additional.HorizontalAndVerticalAlignment
import ru.hits.bdui.domain.screen.components.additional.HorizontalArrangement
import ru.hits.bdui.domain.screen.components.additional.VerticalAlignment
import ru.hits.bdui.domain.screen.components.additional.VerticalArrangement

sealed interface Composite : Component {
    val children: List<Component>
}

data class Row(
    override val children: List<Component>,
    override val base: ComponentBaseProperties,
    val horizontalArrangement: HorizontalArrangement?,
    val verticalAlignment: VerticalAlignment?,
    val isScrollable: Boolean?,
) : Composite {
    override val type: String = "row"
}

data class Box(
    override val children: List<Component>,
    override val base: ComponentBaseProperties,
    val contentAlignment: HorizontalAndVerticalAlignment?
) : Composite {
    override val type: String = "box"
}

data class Column(
    override val children: List<Component>,
    override val base: ComponentBaseProperties,
    val verticalArrangement: VerticalArrangement?,
    val horizontalAlignment: HorizontalAlignment?
) : Composite {
    override val type: String = "column"
}