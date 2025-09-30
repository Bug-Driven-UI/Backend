package ru.hits.bdui.engine.screen.evaluators

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.screen.components.Box
import ru.hits.bdui.domain.screen.components.Button
import ru.hits.bdui.domain.screen.components.Column
import ru.hits.bdui.domain.screen.components.Component
import ru.hits.bdui.domain.screen.components.ComponentBaseProperties
import ru.hits.bdui.domain.screen.components.DynamicColumn
import ru.hits.bdui.domain.screen.components.DynamicRow
import ru.hits.bdui.domain.screen.components.Image
import ru.hits.bdui.domain.screen.components.Input
import ru.hits.bdui.domain.screen.components.ProgressBar
import ru.hits.bdui.domain.screen.components.Row
import ru.hits.bdui.domain.screen.components.Spacer
import ru.hits.bdui.domain.screen.components.StatefulComponent
import ru.hits.bdui.domain.screen.components.Switch
import ru.hits.bdui.domain.screen.components.Text
import ru.hits.bdui.domain.screen.components.additional.Border
import ru.hits.bdui.domain.screen.interactions.actions.Action
import ru.hits.bdui.domain.screen.interactions.actions.CommandAction
import ru.hits.bdui.domain.screen.interactions.actions.NavigateBackAction
import ru.hits.bdui.domain.screen.interactions.actions.NavigateToAction
import ru.hits.bdui.domain.screen.interactions.actions.UpdateScreenAction
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle
import ru.hits.bdui.engine.Interpreter
import ru.hits.bdui.engine.expression.ExpressionUtils.evaluateExpressionIfNeeded

fun ComponentBaseProperties.copyWithEvaluatedProperties(
    interpreter: Interpreter
): ComponentBaseProperties =
    ComponentBaseProperties(
        id = ComponentId(interpreter.evaluateExpressionIfNeeded(id.value)),
        interactions = interactions.map { interaction ->
            interaction.copy(
                actions = interaction.actions.map { action ->
                    action.evaluate(interpreter)
                }
            )
        },
        margins = margins,
        paddings = paddings,
        width = width,
        height = height,
        backgroundColor = backgroundColor?.evaluate(interpreter),
        border = border?.evaluate(interpreter),
        shape = shape,
    )

@Suppress("UNCHECKED_CAST")
fun <T : Component> T.evaluateBaseProperties(
    interpreter: Interpreter,
): T {
    return this.withBase(this.base.copyWithEvaluatedProperties(interpreter)) as T
}

private fun Component.withBase(base: ComponentBaseProperties): Component =
    when (this) {
        is Row -> this.copy(base = base)
        is Box -> this.copy(base = base)
        is Column -> this.copy(base = base)
        is DynamicColumn -> this.copy(base = base)
        is DynamicRow -> this.copy(base = base)
        is Button -> this.copy(base = base)
        is Image -> this.copy(base = base)
        is Input -> this.copy(base = base)
        is ProgressBar -> this.copy(base = base)
        is Spacer -> this.copy(base = base)
        is Switch -> this.copy(base = base)
        is Text -> this.copy(base = base)
        is StatefulComponent -> this.copy(base = base)
    }

fun Action.evaluate(
    interpreter: Interpreter,
): Action {
    return when (this) {
        is CommandAction -> copy(
            params = params.mapValues {
                interpreter.evaluateExpressionIfNeeded(it.value)
            }
        )

        is NavigateBackAction -> this
        is NavigateToAction -> copy(
            screenNavigationParams = screenNavigationParams.mapValues {
                interpreter.evaluateExpressionIfNeeded(it.value)
            }
        )

        is UpdateScreenAction -> copy(
            screenNavigationParams = screenNavigationParams.mapValues {
                interpreter.evaluateExpressionIfNeeded(it.value)
            }
        )
    }
}

fun ColorStyle.evaluate(
    interpreter: Interpreter,
): ColorStyle {
    val evaluatedToken = interpreter.evaluateExpressionIfNeeded(token)
    val evaluatedColor = interpreter.evaluateExpressionIfNeeded(color)
    return ColorStyle(
        token = evaluatedToken,
        color = evaluatedColor,
    )
}

fun Border.evaluate(
    interpreter: Interpreter,
): Border {
    val evaluatedColor = color.evaluate(interpreter)
    return copy(
        color = evaluatedColor,
    )
}

fun TextWithStyle.evaluate(
    interpreter: Interpreter,
): TextWithStyle {
    val evaluatedText = interpreter.evaluateExpressionIfNeeded(text)
    return copy(
        text = evaluatedText,
        color = color.evaluate(interpreter),
    )
}
