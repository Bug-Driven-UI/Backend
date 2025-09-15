package ru.hits.bdui.domain.components

import ru.hits.bdui.domain.components.size.Size
import ru.hits.bdui.parser.visitor.ComponentVisitor

sealed interface Leaf : Component

data class Text(
    val text: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "text"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.visit(this)
}

data class TextField(
    val text: String,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "textField"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.visit(this)
}

data class Image(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "image"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Spacer(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "spacer"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Divider(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "divider"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class ProgressBar(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "progressBar"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Switch(
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "switch"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.default(this)
}

data class Button(
    val text: String,
    val enabled: Boolean,
    override val interactions: List<Interaction>,
    override val insets: Insets,
    override val width: Size,
    override val height: Size
) : Leaf {
    override val type: String = "button"

    override fun <V> accept(visitor: ComponentVisitor<V>): V =
        visitor.visit(this)
}