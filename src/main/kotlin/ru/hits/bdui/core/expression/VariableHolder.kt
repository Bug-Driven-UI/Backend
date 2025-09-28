package ru.hits.bdui.core.expression

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
