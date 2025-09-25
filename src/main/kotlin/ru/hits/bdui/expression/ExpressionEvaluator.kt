package ru.hits.bdui.expression

private const val INTERPOLATION_REGEXP = """(?<!\\)\$\{(.*?)}"""

class ExpressionEvaluator(
    private val interpreter: JSInterpreter,
) {

    private val interpolationRegex = INTERPOLATION_REGEXP.toRegex()

    fun setVariable(name: String, value: String) {
        interpreter.setVariable(name, value)
    }

    fun clearVariables() {
        interpreter.clearVariables()
    }

    fun evaluate(expression: String): String {
        var result = expression
        val matches = interpolationRegex.findAll(expression).toList()
        for (match in matches) {
            val fullMatch = match.value
            val innerExpression = match.groups[1]?.value ?: continue
            val evalResult = interpreter.evaluate(innerExpression).toString()
            result = result.replace(fullMatch, evalResult)
        }
        return result
    }
}