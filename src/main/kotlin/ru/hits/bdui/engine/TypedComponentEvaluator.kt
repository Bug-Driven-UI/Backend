package ru.hits.bdui.engine

import ru.hits.bdui.domain.screen.components.Component

interface ComponentEvaluator {
    val type: String

    fun evaluateComponent(
        component: Component,
        interpreter: Interpreter,
    ): Component
}

interface DoublyTypedComponentEvaluator<TIn : Component, TOut : Component> : ComponentEvaluator {
    override fun evaluateComponent(component: Component, interpreter: Interpreter): Component {
        val typedComponent = component as? TIn ?: throw IllegalArgumentException(
            "Ожидался компонент с типом ${this::class}, но был получен ${component::class}"
        )
        return evaluateTyped(typedComponent, interpreter)
    }

    fun evaluateTyped(
        component: TIn,
        interpreter: Interpreter,
    ): TOut
}

interface TypedComponentEvaluator<T : Component> : DoublyTypedComponentEvaluator<T, T>
