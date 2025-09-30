package ru.hits.bdui.engine.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import org.springframework.stereotype.Component
import ru.hits.bdui.domain.engine.Interpreter
import ru.hits.bdui.engine.expression.evaluateExpression
import ru.hits.bdui.domain.api.Endpoint

@Component
class ExternalApiExpressionUtils(private val objectMapper: ObjectMapper) {

    fun evaluateEndpointExpressions(interpreter: Interpreter, endpoint: Endpoint): Endpoint {
        return endpoint.copy(
            url = interpreter.evaluateExpression(endpoint.url),
            requestBody = endpoint.requestBody?.let { requestBody ->
                traverseJsonNodeAndReplaceExpressions(interpreter, requestBody)
            },
        )
    }

    fun traverseJsonNodeAndReplaceExpressions(interpreter: Interpreter, node: JsonNode): JsonNode {
        return when {
            node.isTextual -> {
                val evaluatedText = interpreter.evaluateExpression(node.asText())
                TextNode.valueOf(evaluatedText)
            }
            node.isObject -> {
                val mappedProperties = node.properties().map { (propertyName, propertyValue) ->
                    propertyName to traverseJsonNodeAndReplaceExpressions(interpreter, propertyValue)
                }.associate { (key, value) -> key to value }
                ObjectNode(objectMapper.nodeFactory, mappedProperties)
            }
            node.isArray -> {
                val elements = mutableListOf<JsonNode>()
                node.elements().forEach { arrayElementNode ->
                    elements.add(traverseJsonNodeAndReplaceExpressions(interpreter, arrayElementNode))
                }
                ArrayNode(objectMapper.nodeFactory, elements)
            }
            else -> {
                node
            }
        }
    }
}
