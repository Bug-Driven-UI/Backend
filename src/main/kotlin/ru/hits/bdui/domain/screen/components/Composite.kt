package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.screen.components.additional.Border
import ru.hits.bdui.domain.screen.components.additional.Shape
import ru.hits.bdui.domain.screen.components.properties.Insets
import ru.hits.bdui.domain.screen.components.properties.Size
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.styles.ColorStyle

sealed interface Composite : Component {
    val children: List<Component>
    val border: Border
    val shape: Shape
}

data class Row(
    override val id: ComponentId,
    override val children: List<Component>,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border,
    override val shape: Shape,
) : Composite {
    override val type: String = "row"
}

data class Box(
    override val id: ComponentId,
    override val children: List<Component>,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border,
    override val shape: Shape,
) : Composite {
    override val type: String = "box"
}

data class Column(
    override val id: ComponentId,
    override val children: List<Component>,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border,
    override val shape: Shape,
) : Composite {
    override val type: String = "column"
}