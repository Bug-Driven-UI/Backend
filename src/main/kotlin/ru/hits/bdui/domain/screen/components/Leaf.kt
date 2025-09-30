package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ValueOrExpression
import ru.hits.bdui.domain.engine.ComponentEvaluator
import ru.hits.bdui.domain.screen.components.additional.Regex
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle
import ru.hits.bdui.engine.screen.component.*

sealed interface Leaf : Component

data class Text(
    val textWithStyle: TextWithStyle,
    private val basePropertiesSet: ComponentPropertiesSet,
) : Leaf, BaseComponentProperties by basePropertiesSet {
    override val type: String = "text"
    override val evaluator: ComponentEvaluator = TextEvaluator()

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}

data class Input(
    val textWithStyle: TextWithStyle,
    val mask: Mask?,
    val regex: Regex?,
    val rightIcon: Image?,
    val hint: Hint?,
    val placeholder: Placeholder?,
    private val basePropertiesSet: ComponentPropertiesSet,
) : Leaf, BaseComponentProperties by basePropertiesSet {
    override val type: String = "textField"
    override val evaluator: ComponentEvaluator = InputEvaluator()

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )

    data class Hint(
        val textWithStyle: TextWithStyle
    )

    data class Placeholder(
        val textWithStyle: TextWithStyle
    )
}

enum class Mask {
    PHONE
}

data class Image(
    val imageUrl: ValueOrExpression,
    val badge: Badge?,
    private val basePropertiesSet: ComponentPropertiesSet,
) : Leaf, BaseComponentProperties by basePropertiesSet {
    override val type: String = "image"
    override val evaluator: ComponentEvaluator = ImageEvaluator()

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )

    sealed interface Badge {
        val type: String

        data class BadgeWithText(
            val textWithStyle: TextWithStyle,
        ) : Badge {
            override val type: String = "badgeWithText"
        }

        data class BadgeWithImage(
            val imageUrl: ValueOrExpression,
        ) : Badge {
            override val type: String = "badgeWithImage"
        }
    }
}

data class Spacer(
    private val basePropertiesSet: ComponentPropertiesSet,
) : Leaf, BaseComponentProperties by basePropertiesSet {
    override val type: String = "spacer"
    override val evaluator: ComponentEvaluator = SpacerEvaluator()

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}

data class ProgressBar(
    private val basePropertiesSet: ComponentPropertiesSet,
) : Leaf, BaseComponentProperties by basePropertiesSet {
    override val type: String = "progressBar"
    override val evaluator: ComponentEvaluator = ProgressBarEvaluator()

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}

data class Switch(
    private val basePropertiesSet: ComponentPropertiesSet,
) : Leaf, BaseComponentProperties by basePropertiesSet {
    override val type: String = "switch"
    override val evaluator: ComponentEvaluator = SwitchEvaluator()

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}

data class Button(
    val textWithStyle: TextWithStyle,
    val enabled: Boolean,
    private val basePropertiesSet: ComponentPropertiesSet,
) : Leaf, BaseComponentProperties by basePropertiesSet {
    override val type: String = "button"
    override val evaluator: ComponentEvaluator = ButtonEvaluator()

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}