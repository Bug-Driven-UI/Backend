package ru.hits.bdui.domain

import ru.hits.bdui.domain.size.Size
import ru.hits.bdui.parser.visitor.ComponentVisitor

sealed interface Component {
    val interactions: List<Interaction>
    val insets: Insets
    val width: Size
    val height: Size

    fun <V> accept(visitor: ComponentVisitor<V>): V
}