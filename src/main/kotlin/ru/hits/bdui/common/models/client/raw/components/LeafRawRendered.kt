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
) : LeafRawRendered

data class InputRawRendered(
    val textWithStyle: RenderedTextWithStyleRaw,
    val mask: RenderedMaskRaw?,
    val regex: RenderedRegexRaw?,
    val rightIcon: ImageRawRendered?,
    val hint: RenderedHintRaw?,
    val placeholder: RenderedPlaceholderRaw?,
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered {
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
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes(
        JsonSubTypes.Type(value = RenderedBadgeRaw.BadgeWithTextRaw::class, name = "badgeWithText"),
        JsonSubTypes.Type(
            value = RenderedBadgeRaw.BadgeWithImageRaw::class,
            name = "badgeWithImage"
        ),
    )
    sealed interface RenderedBadgeRaw {
        data class BadgeWithTextRaw(
            val textWithStyle: RenderedTextWithStyleRaw,
        ) : RenderedBadgeRaw

        data class BadgeWithImageRaw(
            val imageUrl: String,
        ) : RenderedBadgeRaw
    }
}

data class SpacerRawRendered(
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered

data class ProgressBarRawRendered(
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered

data class SwitchRawRendered(
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered

data class ButtonRawRendered(
    val text: TextRawRendered,
    val enabled: Boolean,
    override val base: RenderedComponentBaseRawProperties,
) : LeafRawRendered
