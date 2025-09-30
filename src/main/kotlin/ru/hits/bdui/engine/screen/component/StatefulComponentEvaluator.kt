package ru.hits.bdui.engine.screen.component

import ru.hits.bdui.domain.engine.DoublyTypedComponentEvaluator
import ru.hits.bdui.domain.engine.Interpreter
import ru.hits.bdui.domain.screen.components.Box
import ru.hits.bdui.domain.screen.components.StatefulComponent
import ru.hits.bdui.engine.expression.evaluateExpression

class StatefulComponentEvaluator : DoublyTypedComponentEvaluator<StatefulComponent, Box> {

    override fun evaluateTyped(component: StatefulComponent, interpreter: Interpreter): Box {
        val evaluatedState = component.states.first { state ->
            interpreter.evaluateExpression(state.condition.value).toBoolean()
        }
        return Box(
            children = listOf(
                evaluatedState.component.evaluator.evaluateComponent(evaluatedState.component, interpreter)
            ),
            basePropertiesSet = component.createNewBaseProperties(interpreter),
        )
    }
}
