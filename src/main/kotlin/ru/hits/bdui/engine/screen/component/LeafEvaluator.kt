package ru.hits.bdui.engine.screen.component

import ru.hits.bdui.engine.expression.ExpressionUtils.evaluateExpressionIfNeeded
import ru.hits.bdui.domain.engine.Interpreter
import ru.hits.bdui.domain.engine.TypedComponentEvaluator
import ru.hits.bdui.domain.screen.components.*

interface LeafEvaluator<T: Leaf> : TypedComponentEvaluator<T>

class TextEvaluator : LeafEvaluator<Text> {

    override fun evaluateTyped(component: Text, interpreter: Interpreter): Text {
        return component.evaluateBaseProperties(interpreter).copy(
            textWithStyle = component.textWithStyle.evaluate(interpreter),
        )
    }
}

class InputEvaluator : LeafEvaluator<Input> {

    private val imageEvaluator = ImageEvaluator()

    override fun evaluateTyped(component: Input, interpreter: Interpreter): Input {
        return component.evaluateBaseProperties(interpreter).copy(
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
}

class ImageEvaluator : LeafEvaluator<Image> {

    override fun evaluateTyped(component: Image, interpreter: Interpreter): Image {
        return component.evaluateBaseProperties(interpreter).copy(
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
}

class SpacerEvaluator : LeafEvaluator<Spacer> {
    override fun evaluateTyped(component: Spacer, interpreter: Interpreter): Spacer {
        return component.evaluateBaseProperties(interpreter)
    }
}

class ProgressBarEvaluator : LeafEvaluator<ProgressBar> {
    override fun evaluateTyped(component: ProgressBar, interpreter: Interpreter): ProgressBar {
        return component.evaluateBaseProperties(interpreter)
    }
}

class SwitchEvaluator : LeafEvaluator<Switch> {
    override fun evaluateTyped(component: Switch, interpreter: Interpreter): Switch {
        return component.evaluateBaseProperties(interpreter)
    }
}

class ButtonEvaluator : LeafEvaluator<Button> {
    override fun evaluateTyped(component: Button, interpreter: Interpreter): Button {
        return component.evaluateBaseProperties(interpreter).copy(
            textWithStyle = component.textWithStyle.evaluate(interpreter),
        )
    }
}
