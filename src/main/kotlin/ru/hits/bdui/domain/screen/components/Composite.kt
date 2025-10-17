package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.screen.components.additional.HorizontalArrangement
import ru.hits.bdui.domain.screen.components.additional.HorizontalOrVerticalAlignment
import ru.hits.bdui.domain.screen.components.additional.VerticalArrangement

sealed interface Composite : Component {
    val children: List<Component>
}

data class Row(
    override val children: List<Component>,
    override val base: ComponentBaseProperties,
    val horizontalArrangement: HorizontalArrangement?,
    val verticalAlignment: HorizontalOrVerticalAlignment.VerticalAlignment?
) : Composite {
    override val type: String = "row"
}

data class Box(
    override val children: List<Component>,
    override val base: ComponentBaseProperties,
    val contentAlignment: HorizontalOrVerticalAlignment?
) : Composite {
    override val type: String = "box"
}

data class Column(
    override val children: List<Component>,
    override val base: ComponentBaseProperties,
    val verticalArrangement: VerticalArrangement?,
    val horizontalAlignment: HorizontalOrVerticalAlignment.HorizontalAlignment?
) : Composite {
    override val type: String = "column"
}