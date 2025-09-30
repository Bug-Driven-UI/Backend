package ru.hits.bdui.engine.screen.component

import ru.hits.bdui.engine.expression.ExpressionUtils.evaluateExpressionIfNeeded
import ru.hits.bdui.domain.engine.Interpreter
import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.screen.components.BaseComponentProperties
import ru.hits.bdui.domain.screen.components.Component
import ru.hits.bdui.domain.screen.components.ComponentPropertiesSet
import ru.hits.bdui.domain.screen.components.additional.Border
import ru.hits.bdui.domain.screen.interactions.actions.*
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle

fun BaseComponentProperties.createNewBaseProperties(
    interpreter: Interpreter
): ComponentPropertiesSet {
    return ComponentPropertiesSet(
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
}

@Suppress("UNCHECKED_CAST")
fun <T: Component> T.evaluateBaseProperties(
    interpreter: Interpreter,
) : T {
    return copyWithNewBaseProperties(newProperties = createNewBaseProperties(interpreter)) as T
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
