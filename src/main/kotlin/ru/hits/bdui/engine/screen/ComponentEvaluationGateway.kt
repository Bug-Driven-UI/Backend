package ru.hits.bdui.engine.screen

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.TextNode
import ru.hits.bdui.domain.screen.components.Box
import ru.hits.bdui.domain.screen.components.Column
import ru.hits.bdui.domain.screen.components.Component
import ru.hits.bdui.domain.screen.components.Composite
import ru.hits.bdui.domain.screen.components.DynamicColumn
import ru.hits.bdui.domain.screen.components.DynamicComposite
import ru.hits.bdui.domain.screen.components.DynamicRow
import ru.hits.bdui.domain.screen.components.Leaf
import ru.hits.bdui.domain.screen.components.Row
import ru.hits.bdui.domain.screen.components.StatefulComponent
import ru.hits.bdui.engine.ComponentEvaluator
import ru.hits.bdui.engine.Interpreter
import ru.hits.bdui.engine.expression.evaluateExpression
import ru.hits.bdui.engine.screen.evaluators.copyWithEvaluatedProperties

interface ComponentEvaluationGateway {
    fun evaluate(component: Component, interpreter: Interpreter): Component
}

@org.springframework.stereotype.Component
class ComponentEvaluationGatewayImpl(
    private val objectMapper: ObjectMapper,
    evaluators: List<ComponentEvaluator>
) : ComponentEvaluationGateway {
    private val typeToEvaluator = evaluators.associateBy { it.type }

    override fun evaluate(component: Component, interpreter: Interpreter): Component =
        when (component) {
            is Leaf -> getEvaluator(component).evaluateComponent(component, interpreter)

            is Composite -> {
                val evaluatedChildren = component.children.map { evaluate(it, interpreter) }
                val withChildren = component.withChildren(evaluatedChildren)
                getEvaluator(component).evaluateComponent(withChildren, interpreter)
            }

            is StatefulComponent -> {
                val evaluatedStates = component.states.first { st ->
                    interpreter.evaluateExpression(st.condition.value).toBoolean()
                }
                val evaluatedChild = evaluate(evaluatedStates.component, interpreter)

                Box(
                    children = listOf(evaluatedChild),
                    base = component.base.copyWithEvaluatedProperties(interpreter),
                )
            }

            is DynamicColumn -> {
                val evaluatedChildren = evaluateDynamicChildren(component, interpreter)

                Column(
                    children = evaluatedChildren,
                    base = component.base.copyWithEvaluatedProperties(interpreter)
                )
            }

            is DynamicRow -> {
                val evaluatedChildren = evaluateDynamicChildren(component, interpreter)

                Row(
                    children = evaluatedChildren,
                    base = component.base.copyWithEvaluatedProperties(interpreter)
                )
            }
        }

    private fun getEvaluator(component: Component): ComponentEvaluator =
        typeToEvaluator[component.type]
            ?: error("Нет обработчика для ${component.type}")

    private fun evaluateDynamicChildren(component: DynamicComposite, interpreter: Interpreter): List<Component> {
        val items = readItems(component, interpreter)
        return items.map { item ->
            interpreter.scope { scoped ->
                scoped.setVariable(component.itemAlias, item)
                evaluate(component.itemTemplate.component, scoped)
            }
        }
    }

    //TODO(Добавить санитизацию выражений)
    private fun readItems(component: DynamicComposite, interpreter: Interpreter): List<JsonNode> {
        val lengthExpr = "Java.from(${component.itemsData}).length"
        val len = interpreter.execute(lengthExpr)?.toIntOrNull() ?: 0
        return (0 until len).map { idx ->
            val itemExpr = "JSON.stringify(Java.from(${component.itemsData})[$idx])"
            val raw = interpreter.execute(itemExpr)
            runCatching { objectMapper.readTree(raw) }.getOrElse { TextNode(raw) }
        }
    }

    private fun Composite.withChildren(children: List<Component>): Composite = when (this) {
        is Row -> this.copy(children = children)
        is Column -> this.copy(children = children)
        is Box -> this.copy(children = children)
    }
}