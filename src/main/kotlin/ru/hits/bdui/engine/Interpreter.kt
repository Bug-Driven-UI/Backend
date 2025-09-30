package ru.hits.bdui.engine

import com.fasterxml.jackson.databind.JsonNode

interface Interpreter {

    fun setVariable(name: String, value: String)

    fun setVariable(name: String, value: JsonNode)

    fun getVariable(name: String): Any?

    fun clearVariables()

    fun clearVariable(name: String)

    fun execute(expression: String): String?

    fun <T> scope(block: (ScopedInterpreter) -> T): T
}

interface ScopedInterpreter : Interpreter

fun Interpreter.setVariables(variables: Map<String, JsonNode>) {
    variables.forEach { (name, value) ->
        setVariable(name, value)
    }
}
