package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.properties.Insets
import ru.hits.bdui.domain.screen.properties.Size

sealed interface Composite : Component {
    val children: List<Component>
}

data class Row(
    override val id: ComponentId,
    override val hash: String,
    override val children: List<Component>,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Composite {
    override val type: String = "row"
}

data class Box(
    override val id: ComponentId,
    override val hash: String,
    override val children: List<Component>,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Composite {
    override val type: String = "box"
}

data class Column(
    override val id: ComponentId,
    override val hash: String,
    override val children: List<Component>,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Composite {
    override val type: String = "column"
}