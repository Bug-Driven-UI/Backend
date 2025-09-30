package ru.hits.bdui.engine.screen.evaluators

import ru.hits.bdui.domain.screen.components.Box
import ru.hits.bdui.domain.screen.components.Column
import ru.hits.bdui.domain.screen.components.Composite
import ru.hits.bdui.domain.screen.components.Row
import ru.hits.bdui.engine.Interpreter
import ru.hits.bdui.engine.TypedComponentEvaluator

abstract class CompositeEvaluator<T : Composite> : TypedComponentEvaluator<T> {
    override fun evaluateTyped(component: T, interpreter: Interpreter): T =
        component.evaluateBaseProperties(interpreter)
}

@org.springframework.stereotype.Component
class RowEvaluator : CompositeEvaluator<Row>() {
    override val type: String = "row"
}

@org.springframework.stereotype.Component
class ColumnEvaluator : CompositeEvaluator<Column>() {
    override val type: String = "column"
}

@org.springframework.stereotype.Component
class BoxEvaluator : CompositeEvaluator<Box>() {
    override val type: String = "box"
}