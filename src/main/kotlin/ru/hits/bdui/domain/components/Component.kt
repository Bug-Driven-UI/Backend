package ru.hits.bdui.domain.components

import ru.hits.bdui.domain.components.size.Size
import ru.hits.bdui.parser.visitor.ComponentVisitor

sealed interface Component {
    val type: String
    val interactions: List<Interaction>
    val insets: Insets
    val width: Size
    val height: Size

    fun <V> accept(visitor: ComponentVisitor<V>): V
}