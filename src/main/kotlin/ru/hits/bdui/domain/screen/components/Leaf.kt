package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.ValueOrExpression
import ru.hits.bdui.domain.screen.components.additional.Border
import ru.hits.bdui.domain.screen.components.additional.Shape
import ru.hits.bdui.domain.screen.components.properties.Insets
import ru.hits.bdui.domain.screen.components.properties.Size
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.styles.ColorStyle
import ru.hits.bdui.domain.screen.styles.TextStyle

sealed interface Leaf : Component

data class Text(
    val text: ValueOrExpression,
    val textStyle: TextStyle,
    val color: ColorStyle,
    override val id: ComponentId,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border?,
    override val shape: Shape?,
) : Leaf {
    override val type: String = "text"
}

data class Input(
    val text: ValueOrExpression,
    val textStyle: TextStyle,
    val color: ColorStyle,
    val mask: String,
    val regex: Regex?,
    val rightIcon: Image,
    val hint: Hint?,
    val placeholder: Placeholder?,
    override val id: ComponentId,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border?,
    override val shape: Shape?,
) : Leaf {
    override val type: String = "textField"

    data class Hint(
        val text: ValueOrExpression,
        val textStyle: TextStyle,
        val color: ColorStyle,
    )

    data class Placeholder(
        val text: ValueOrExpression,
        val textStyle: TextStyle,
        val color: ColorStyle,
    )
}

data class Image(
    val imageUrl: ValueOrExpression,
    val badge: Badge?,
    override val id: ComponentId,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border?,
    override val shape: Shape?,
) : Leaf {
    override val type: String = "image"

    data class Badge(
        val text: ValueOrExpression,
        val textStyle: TextStyle,
        val color: ColorStyle,
        val imageUrl: String,
    )
}

data class Spacer(
    override val id: ComponentId,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border?,
    override val shape: Shape?,
) : Leaf {
    override val type: String = "spacer"
}

data class ProgressBar(
    override val id: ComponentId,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border?,
    override val shape: Shape?,
) : Leaf {
    override val type: String = "progressBar"
}

data class Switch(
    override val id: ComponentId,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border?,
    override val shape: Shape?,
) : Leaf {
    override val type: String = "switch"
}

data class Button(
    val text: ValueOrExpression,
    val textStyle: TextStyle,
    val color: ColorStyle,
    val enabled: Boolean,
    override val id: ComponentId,
    override val interactions: List<Interaction>,
    override val insets: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border?,
    override val shape: Shape?,
) : Leaf {
    override val type: String = "button"
}