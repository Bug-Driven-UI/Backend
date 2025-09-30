package ru.hits.bdui.domain.engine

import ru.hits.bdui.domain.screen.components.Component

interface ComponentEvaluator {

    fun evaluateComponent(
        component: Component,
        interpreter: Interpreter,
    ): Component
}

interface DoublyTypedComponentEvaluator<T: Component, R: Component> : ComponentEvaluator {

    override fun evaluateComponent(component: Component, interpreter: Interpreter): Component {
        val typedComponent = component as? T ?: throw IllegalArgumentException(
            "Expected component of type ${this::class}, but got ${component::class}"
        )
        return evaluateTyped(typedComponent, interpreter)
    }

    fun evaluateTyped(
        component: T,
        interpreter: Interpreter,
    ): R
}

interface TypedComponentEvaluator<T: Component> : DoublyTypedComponentEvaluator<T, T>
