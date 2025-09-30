package ru.hits.bdui.engine.screen.component

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ru.hits.bdui.domain.engine.DoublyTypedComponentEvaluator
import ru.hits.bdui.domain.engine.Interpreter
import ru.hits.bdui.domain.screen.components.*

class DynamicCompositeEvaluator : DoublyTypedComponentEvaluator<DynamicComposite, Composite> {

    //TODO DI
    private val objectMapper = jacksonObjectMapper()

    override fun evaluateTyped(component: DynamicComposite, interpreter: Interpreter): Composite {
        val itemCount = getDynamicListLength(component, interpreter)
        val itemsData = (0 until itemCount).map { index ->
            getDynamicListItem(component, index, interpreter)
        }
        return when(component) {
            is DynamicColumn -> Column(
                children = getComponentsList(
                    interpreter = interpreter,
                    itemsData = itemsData,
                    itemAlias = component.itemAlias,
                    itemTemplate = component.itemTemplate.component,
                ),
                basePropertiesSet = component.createNewBaseProperties(interpreter),
            )
            is DynamicRow -> Row(
                children = getComponentsList(
                    interpreter = interpreter,
                    itemsData = itemsData,
                    itemAlias = component.itemAlias,
                    itemTemplate = component.itemTemplate.component,
                ),
                basePropertiesSet = component.createNewBaseProperties(interpreter),
            )
        }
    }

    //TODO input sanitization?
    private fun getDynamicListLength(component: DynamicComposite, interpreter: Interpreter): Int {
        val lengthExpression = "Java.from(${component.itemsData}).length"
        val lengthValue = interpreter.execute(lengthExpression)
        return lengthValue?.toIntOrNull() ?: 0
    }

    //TODO input sanitization?
    private fun getDynamicListItem(component: DynamicComposite, index: Int, interpreter: Interpreter): JsonNode {
        val itemExpression = "JSON.stringify(Java.from(${component.itemsData})[$index])"
        val rawItem = interpreter.execute(itemExpression)
        return runCatching {
            objectMapper.readTree(rawItem)
        }.getOrElse {
            TextNode(rawItem)
        }
    }

    private fun getComponentsList(
        interpreter: Interpreter,
        itemsData: List<JsonNode>,
        itemAlias: String,
        itemTemplate: Component,
    ): List<Component> {
        return itemsData.map { itemData ->
            interpreter.scope { scopedInterpreter ->
                scopedInterpreter.setVariable(itemAlias, itemData)
                itemTemplate.evaluator.evaluateComponent(itemTemplate, scopedInterpreter)
            }
        }
    }
}
