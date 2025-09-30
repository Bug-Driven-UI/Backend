package ru.hits.bdui.engine.screen.evaluators

import ru.hits.bdui.domain.screen.components.Button
import ru.hits.bdui.domain.screen.components.Image
import ru.hits.bdui.domain.screen.components.Input
import ru.hits.bdui.domain.screen.components.Leaf
import ru.hits.bdui.domain.screen.components.ProgressBar
import ru.hits.bdui.domain.screen.components.Spacer
import ru.hits.bdui.domain.screen.components.Switch
import ru.hits.bdui.domain.screen.components.Text
import ru.hits.bdui.engine.Interpreter
import ru.hits.bdui.engine.TypedComponentEvaluator
import ru.hits.bdui.engine.expression.ExpressionUtils.evaluateExpressionIfNeeded

interface LeafEvaluator<T : Leaf> : TypedComponentEvaluator<T>

@org.springframework.stereotype.Component
class TextEvaluator : LeafEvaluator<Text> {
    override val type: String = "text"

    override fun evaluateTyped(component: Text, interpreter: Interpreter): Text {
        return component.evaluateBaseProperties(interpreter).copy(
            textWithStyle = component.textWithStyle.evaluate(interpreter),
        )
    }
}

@org.springframework.stereotype.Component
class InputEvaluator : LeafEvaluator<Input> {
    override val type: String = "input"

    private val imageEvaluator = ImageEvaluator()

    override fun evaluateTyped(component: Input, interpreter: Interpreter): Input =
        component.evaluateBaseProperties(interpreter).copy(
            textWithStyle = component.textWithStyle.evaluate(interpreter),
            rightIcon = component.rightIcon?.let { imageEvaluator.evaluateTyped(it, interpreter) },
            hint = component.hint?.copy(
                textWithStyle = component.hint.textWithStyle.evaluate(interpreter),
            ),
            placeholder = component.placeholder?.copy(
                textWithStyle = component.placeholder.textWithStyle.evaluate(interpreter),
            ),
        )
}

@org.springframework.stereotype.Component
class ImageEvaluator : LeafEvaluator<Image> {
    override val type: String = "image"

    override fun evaluateTyped(component: Image, interpreter: Interpreter): Image =
        component.evaluateBaseProperties(interpreter).copy(
            imageUrl = interpreter.evaluateExpressionIfNeeded(component.imageUrl),
            badge = when (component.badge) {
                is Image.Badge.BadgeWithImage -> Image.Badge.BadgeWithImage(
                    imageUrl = interpreter.evaluateExpressionIfNeeded(component.badge.imageUrl),
                )

                is Image.Badge.BadgeWithText -> Image.Badge.BadgeWithText(
                    textWithStyle = component.badge.textWithStyle.evaluate(interpreter),
                )

                null -> null
            },
        )
}

@org.springframework.stereotype.Component
class SpacerEvaluator : LeafEvaluator<Spacer> {
    override val type: String = "spacer"

    override fun evaluateTyped(component: Spacer, interpreter: Interpreter): Spacer =
        component.evaluateBaseProperties(interpreter)
}

@org.springframework.stereotype.Component
class ProgressBarEvaluator : LeafEvaluator<ProgressBar> {
    override val type: String = "progressBar"

    override fun evaluateTyped(component: ProgressBar, interpreter: Interpreter): ProgressBar =
        component.evaluateBaseProperties(interpreter)
}

@org.springframework.stereotype.Component
class SwitchEvaluator : LeafEvaluator<Switch> {
    override val type: String = "switch"

    override fun evaluateTyped(component: Switch, interpreter: Interpreter): Switch =
        component.evaluateBaseProperties(interpreter)

}

@org.springframework.stereotype.Component
class ButtonEvaluator : LeafEvaluator<Button> {
    override val type: String = "button"

    override fun evaluateTyped(component: Button, interpreter: Interpreter): Button =
        component.evaluateBaseProperties(interpreter)
            .copy(textWithStyle = component.textWithStyle.evaluate(interpreter))
}
