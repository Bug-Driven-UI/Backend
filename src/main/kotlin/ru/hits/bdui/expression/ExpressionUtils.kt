package ru.hits.bdui.expression

import ru.hits.bdui.common.exceptions.BadRequestException
import ru.hits.bdui.domain.Expression
import ru.hits.bdui.domain.Value
import ru.hits.bdui.domain.ValueOrExpression

object ExpressionUtils {
    const val INTERPOLATION_REGEXP = """(?<!\\)\$\{(.*?)}"""
    private val regex = INTERPOLATION_REGEXP.toRegex()

    fun isExpression(obj: Any): Boolean =
        obj is String && obj.matches(regex)

    fun containsExpression(obj: Any): Boolean =
        obj is String && regex.containsMatchIn(obj)

    fun getValueOrExpression(value: Any): ValueOrExpression =
        if (isExpression(value)) Expression(value as String)
        else Value(value)

    fun getExpressionOrThrow(value: String): Expression =
        if (isExpression(value)) Expression(value)
        else throw BadRequestException("Значение $value не является выражением")
}