package ru.hits.bdui.parser.visitor

import com.fasterxml.jackson.databind.JsonNode
import org.slf4j.LoggerFactory
import ru.hits.bdui.domain.Box
import ru.hits.bdui.domain.Button
import ru.hits.bdui.domain.Column
import ru.hits.bdui.domain.Component
import ru.hits.bdui.domain.Row
import ru.hits.bdui.domain.Text
import ru.hits.bdui.domain.TextField

/**
 * Подменяет переменные
 */
class VariableReplacer(
    private val responseNameToValue: Map<String, JsonNode>
) : ComponentVisitor<Component> {
    private val log = LoggerFactory.getLogger(this::class.java)

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

    private fun replaceVars(variable: String): String =
        if (variable.startsWith("$")) {
            val keyList = variable
                .removePrefix("$")
                .split(".")

            responseNameToValue[keyList[0]]
                ?.let { jsonNode ->
                    kotlin.runCatching {
                        VariableValueExtractor.extract(keyList.subList(1, keyList.size), jsonNode)
                    }
                        .onFailure { log.warn("Не удалось извлечь значение переменной: ${keyList[0]}") }
                        .getOrNull()
                        ?.toString()
                }
                ?: throw VariableReplacerException("Н")

        } else {
            variable
        }
}

class VariableReplacerException(message: String) : Exception(message)