package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.properties.Insets
import ru.hits.bdui.domain.screen.properties.Size
import ru.hits.bdui.domain.screen.styles.ColorStyle
import ru.hits.bdui.domain.screen.styles.TextStyle

sealed interface Leaf : Component

data class Text(
    val text: String,
    val textStyle: TextStyle,
    val color: ColorStyle,
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "text"
}

data class TextField(
    val text: String,
    val textStyle: TextStyle,
    val color: ColorStyle,
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "textField"
}

data class Image(
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "image"
}

data class Spacer(
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "spacer"
}

data class Divider(
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "divider"
}

data class ProgressBar(
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "progressBar"
}

data class Switch(
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "switch"
}

data class Button(
    val text: String,
    val enabled: Boolean,
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "button"
}