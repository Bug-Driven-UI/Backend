package ru.hits.bdui.common.models.client.raw.components

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedRegexRaw
import ru.hits.bdui.common.models.client.raw.styles.text.RenderedTextWithStyleRaw

sealed interface LeafRawRendered : RenderedComponentRaw

data class TextRawRendered(
    val textWithStyle: RenderedTextWithStyleRaw,
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered {
    override val type: String = "text"
}

data class InputRawRendered(
    val textWithStyle: RenderedTextWithStyleRaw,
    val mask: RenderedMaskRaw?,
    val regex: RenderedRegexRaw?,
    val rightIcon: ImageRawRendered?,
    val hint: RenderedHintRaw?,
    val placeholder: RenderedPlaceholderRaw?,
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered {
    override val type: String = "input"

    data class RenderedHintRaw(
        val textWithStyle: RenderedTextWithStyleRaw
    )

    data class RenderedPlaceholderRaw(
        val textWithStyle: RenderedTextWithStyleRaw
    )
}

enum class RenderedMaskRaw {
    @JsonProperty("phone")
    PHONE
}

data class ImageRawRendered(
    val imageUrl: String,
    val badge: RenderedBadgeRaw?,
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered {
    override val type: String = "image"

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes(
        JsonSubTypes.Type(value = RenderedBadgeRaw.BadgeWithTextRaw::class, name = "badgeWithText"),
        JsonSubTypes.Type(
            value = RenderedBadgeRaw.BadgeWithImageRaw::class,
            name = "badgeWithImage"
        ),
    )
    sealed interface RenderedBadgeRaw {
        val type: String

        data class BadgeWithTextRaw(
            val textWithStyle: RenderedTextWithStyleRaw,
        ) : RenderedBadgeRaw {
            override val type: String = "badgeWithText"
        }

        data class BadgeWithImageRaw(
            val imageUrl: String,
        ) : RenderedBadgeRaw {
            override val type: String = "badgeWithImage"
        }
    }
}

data class SpacerRawRendered(
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered {
    override val type: String = "spacer"
}

data class ProgressBarRawRendered(
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered {
    override val type: String = "progressBar"
}

data class SwitchRawRendered(
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered {
    override val type: String = "switch"
}

data class ButtonRawRendered(
    val text: TextRawRendered,
    val enabled: Boolean,
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered {
    override val type: String = "button"
}
