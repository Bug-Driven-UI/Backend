package ru.hits.bdui.parser.visitor

import ru.hits.bdui.domain.components.Box
import ru.hits.bdui.domain.components.Button
import ru.hits.bdui.domain.components.Column
import ru.hits.bdui.domain.components.Component
import ru.hits.bdui.domain.components.Row
import ru.hits.bdui.domain.components.Text
import ru.hits.bdui.domain.components.TextField

interface ComponentVisitor<V> {
    fun visit(text: Text): V
    fun visit(textField: TextField): V
    fun visit(button: Button): V
    fun visit(row: Row): V
    fun visit(column: Column): V
    fun visit(box: Box): V

    fun default(component: Component): V
}

