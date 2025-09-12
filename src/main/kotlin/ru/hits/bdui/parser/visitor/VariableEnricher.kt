package ru.hits.bdui.parser.visitor

import ru.hits.bdui.domain.Box
import ru.hits.bdui.domain.Button
import ru.hits.bdui.domain.Column
import ru.hits.bdui.domain.Component
import ru.hits.bdui.domain.Row
import ru.hits.bdui.domain.Text
import ru.hits.bdui.domain.TextField

class VariableEnricher(
    private val values: Map<String, String>
) : ComponentVisitor<Component> {
    override fun visit(text: Text): Component =
        text.copy(text = replaceVars(text.text))

    override fun visit(textField: TextField): Component =
        textField.copy(text = replaceVars(textField.text))

    override fun visit(button: Button): Component =
        button.copy(text = replaceVars(button.text))

    override fun visit(row: Row): Component =
        row.copy(children = row.children.map { it.accept(this) })

    override fun visit(column: Column): Component =
        column.copy(children = column.children.map { it.accept(this) })

    override fun visit(box: Box): Component =
        box.copy(children = box.children.map { it.accept(this) })

    /**
     * Для компонентов, у которых не может быть переменных
     */
    override fun default(component: Component): Component = component

    private fun replaceVars(text: String): String =
        text
            .split(" ")
            .joinToString(" ") { part ->
                if (part.startsWith("$"))
                    values[part.removePrefix("$")] ?: part
                else
                    part
            }
}