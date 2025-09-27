package ru.hits.bdui.expression

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory
import org.slf4j.LoggerFactory

class JSInterpreter(
    private val objectMapper: ObjectMapper,
) {

    private val engine = NashornScriptEngineFactory().scriptEngine
    private val log = LoggerFactory.getLogger(this::class.java)

    fun setVariable(name: String, value: String) {
        runCatching {
            objectMapper.readTree(value)
        }.onSuccess { jsonNode ->
            when {
                jsonNode.isFloatingPointNumber -> engine.put(name, jsonNode.doubleValue())
                jsonNode.isIntegralNumber -> engine.put(name, jsonNode.longValue())
                jsonNode.isBoolean -> engine.put(name, jsonNode.booleanValue())
                jsonNode.isNull -> engine.put(name, null)
                jsonNode.isTextual -> engine.put(name, jsonNode.textValue())
                jsonNode.isArray -> {
                    val convertedValue = objectMapper.convertValue<Array<Any>>(jsonNode)
                    engine.put(name, convertedValue)
                }
                else -> {
                    val convertedValue = objectMapper.convertValue<Map<String, Any>>(jsonNode)
                    engine.put(name, convertedValue)
                }
            }
        }.onFailure {
            log.warn("Failed to parse variable $name as a valid JSON node. Storing as string.")
            engine.put(name, value)
        }
    }

    fun clearVariables() {
        engine.getBindings(javax.script.ScriptContext.ENGINE_SCOPE).clear()
    }

    fun evaluate(script: String): String? {
        return engine.eval(script)?.toString()
    }
}
