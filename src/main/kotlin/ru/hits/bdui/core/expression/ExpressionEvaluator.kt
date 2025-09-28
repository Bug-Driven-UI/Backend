package ru.hits.bdui.core.expression

private const val INTERPOLATION_REGEXP = """(?<!\\)\$\{(.*?)}"""

private val interpolationRegex = INTERPOLATION_REGEXP.toRegex()

fun Interpreter.evaluateExpression(expression: String): String {
    var result = expression
    val matches = interpolationRegex.findAll(expression).toList()
    for (match in matches) {
        val fullMatch = match.value
        val innerExpression = match.groups[1]?.value ?: continue
        val evalResult = execute(innerExpression).toString()
        result = result.replace(fullMatch, evalResult)
    }
    return result
}
