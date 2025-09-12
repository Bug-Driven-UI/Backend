package ru.hits.bdui.parser.visitor

import ru.hits.bdui.domain.Box
import ru.hits.bdui.domain.Button
import ru.hits.bdui.domain.Column
import ru.hits.bdui.domain.Component
import ru.hits.bdui.domain.Row
import ru.hits.bdui.domain.Text
import ru.hits.bdui.domain.TextField

interface ComponentVisitor<V> {
    fun visit(text: Text): V
    fun visit(textField: TextField): V
    fun visit(button: Button): V
    fun visit(row: Row): V
    fun visit(column: Column): V
    fun visit(box: Box): V

    fun default(component: Component): V
}

