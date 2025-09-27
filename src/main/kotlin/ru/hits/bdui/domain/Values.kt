package ru.hits.bdui.domain

@JvmInline
value class ScreenId(val value: String)

@JvmInline
value class ScreenName(val value: String)

@JvmInline
value class ComponentId(val value: String)

@JvmInline
value class CommandName(val value: String)

@JvmInline
value class ApiName(val value: String)

@JvmInline
value class TemplateName(val value: String)


/**
 * Интерфейс для обозначения полей, которые могут быть заданы и как выражение и как конкретное значение
 */
sealed interface ValueOrExpression {
    val value: String
}

@JvmInline
value class Expression(override val value: String) : ValueOrExpression

@JvmInline
value class Value(override val value: String) : ValueOrExpression