package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ValueOrExpression
import ru.hits.bdui.domain.screen.components.additional.Regex
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle

sealed interface Leaf : Component

data class Text(
    val textWithStyle: TextWithStyle,
    override val base: ComponentBaseProperties,
) : Leaf {
    override val type: String = "text"
}

data class Input(
    val textWithStyle: TextWithStyle,
    val mask: Mask?,
    val regex: Regex?,
    val rightIcon: Image?,
    val hint: Hint?,
    val placeholder: Placeholder?,
    override val base: ComponentBaseProperties,
) : Leaf {
    override val type: String = "textField"

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
    override val base: ComponentBaseProperties,
) : Leaf {
    override val type: String = "image"

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
    override val base: ComponentBaseProperties,
) : Leaf {
    override val type: String = "spacer"
}

data class ProgressBar(
    override val base: ComponentBaseProperties,
) : Leaf {
    override val type: String = "progressBar"
}

data class Switch(
    override val base: ComponentBaseProperties,
) : Leaf {
    override val type: String = "switch"
}

data class Button(
    val text: Text,
    val enabled: Boolean,
    override val base: ComponentBaseProperties,
) : Leaf {
    override val type: String = "button"
}