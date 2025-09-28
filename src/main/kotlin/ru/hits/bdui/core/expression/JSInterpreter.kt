package ru.hits.bdui.core.expression

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import javax.script.ScriptContext.ENGINE_SCOPE
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory
import org.slf4j.LoggerFactory
import javax.script.ScriptEngine

class JSInterpreter(
    private val objectMapper: ObjectMapper,
) : Interpreter {

    private val engine = NashornScriptEngineFactory().scriptEngine
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun setVariable(name: String, value: String) {
        runCatching {
            objectMapper.readTree(value)
        }.onSuccess { jsonNode ->
            setVariable(name, jsonNode)
        }.onFailure {
            log.warn("Failed to parse variable $name as a valid JSON node. Storing as string.")
            engine.put(name, value)
        }
    }

    override fun setVariable(name: String, value: JsonNode) {
        when {
            value.isFloatingPointNumber -> engine.put(name, value.doubleValue())
            value.isIntegralNumber -> engine.put(name, value.longValue())
            value.isBoolean -> engine.put(name, value.booleanValue())
            value.isNull -> engine.put(name, null)
            value.isTextual -> engine.put(name, value.textValue())
            value.isArray -> {
                val convertedValue = objectMapper.convertValue<Array<Any>>(value)
                engine.put(name, convertedValue)
            }
            else -> {
                val convertedValue = objectMapper.convertValue<Map<String, Any>>(value)
                engine.put(name, convertedValue)
            }
        }
    }

    override fun getVariable(name: String): Any? {
        return engine.getBindings(ENGINE_SCOPE)[name]
    }

    override fun clearVariable(name: String) {
        engine.getBindings(ENGINE_SCOPE).remove(name)
    }

    override fun clearVariables() {
        engine.getBindings(ENGINE_SCOPE).clear()
    }

    override fun execute(expression: String): String? {
        return engine.eval(expression)?.toString()
    }

    override fun <T> scope(block: (ScopedInterpreter) -> T): T = synchronized(this) {
        val scopedEvaluator = ScopedJSInterpreter(objectMapper, this, engine)
        val result = block(scopedEvaluator)
        scopedEvaluator.clearVariables()
        return@synchronized result
    }
}

class ScopedJSInterpreter(
    private val objectMapper: ObjectMapper,
    private val parentInterpreter: Interpreter,
    private val engine: ScriptEngine,
) : ScopedInterpreter, Interpreter by parentInterpreter {

    private val shadowedVariables = mutableMapOf<String, Any>()
    private val addedVariables = mutableSetOf<String>()

    override fun setVariable(name: String, value: String) {
        val existingVariable = parentInterpreter.getVariable(name)
        if (existingVariable != null) {
            shadowedVariables[name] = existingVariable
        } else {
            addedVariables.add(name)
        }
        parentInterpreter.setVariable(name, value)
    }

    override fun setVariable(name: String, value: JsonNode) {
        val existingVariable = parentInterpreter.getVariable(name)
        if (existingVariable != null) {
            shadowedVariables[name] = existingVariable
        } else {
            addedVariables.add(name)
        }
        parentInterpreter.setVariable(name, value)
    }

    override fun clearVariables() {
        addedVariables.forEach {
            parentInterpreter.clearVariable(it)
        }
        shadowedVariables.forEach { (name, originalValue) ->
            engine.put(name, originalValue)
        }
        addedVariables.clear()
        shadowedVariables.clear()
    }

    override fun clearVariable(name: String) {
        if (addedVariables.contains(name)) {
            parentInterpreter.clearVariable(name)
            addedVariables.remove(name)
        } else if (shadowedVariables.containsKey(name)) {
            val originalValue = shadowedVariables.remove(name)
            engine.put(name, originalValue)
        }
    }

    override fun <T> scope(block: (ScopedInterpreter) -> T): T = synchronized(this) {
        val scopedEvaluator = ScopedJSInterpreter(objectMapper, this, engine)
        val result = block(scopedEvaluator)
        scopedEvaluator.clearVariables()
        return@synchronized result
    }
}
