package ru.hits.bdui.engine.screen.component

import ru.hits.bdui.domain.engine.Interpreter
import ru.hits.bdui.domain.engine.TypedComponentEvaluator
import ru.hits.bdui.domain.screen.components.Box
import ru.hits.bdui.domain.screen.components.Column
import ru.hits.bdui.domain.screen.components.Composite
import ru.hits.bdui.domain.screen.components.Row

class CompositeEvaluator : TypedComponentEvaluator<Composite> {

    override fun evaluateTyped(component: Composite, interpreter: Interpreter): Composite {
        val newBase = component.createNewBaseProperties(interpreter)
        val newChildren = component.children.map { child ->
            child.evaluator.evaluateComponent(child, interpreter)
        }
        return when (component) {
            is Box -> Box(
                children = newChildren,
                basePropertiesSet = newBase,
            )
            is Column -> Column(
                children = newChildren,
                basePropertiesSet = newBase,
            )
            is Row -> Row(
                children = newChildren,
                basePropertiesSet = newBase,
            )
        }
    }
}
