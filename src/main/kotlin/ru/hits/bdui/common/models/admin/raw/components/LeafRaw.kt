package ru.hits.bdui.common.models.admin.raw.components

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.admin.raw.components.additional.RegexRaw
import ru.hits.bdui.common.models.admin.raw.styles.text.TextWithStyleRaw

sealed interface LeafRaw : ComponentRaw

data class TextRaw(
    val textWithStyle: TextWithStyleRaw,
    override val base: ComponentBaseRawProperties,
) : LeafRaw

data class InputRaw(
    val textWithStyle: TextWithStyleRaw,
    val mask: MaskRaw?,
    val regex: RegexRaw?,
    val rightIcon: ImageRaw?,
    val hint: HintRaw?,
    val placeholder: PlaceholderRaw?,
    override val base: ComponentBaseRawProperties,
) : LeafRaw {
    data class HintRaw(
        val textWithStyle: TextWithStyleRaw
    )

    data class PlaceholderRaw(
        val textWithStyle: TextWithStyleRaw
    )
}

enum class MaskRaw {
    @JsonProperty("phone")
    PHONE
}

data class ImageRaw(
    val imageUrl: String,
    val badge: BadgeRaw?,
    override val base: ComponentBaseRawProperties,
) : LeafRaw {
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes(
        JsonSubTypes.Type(value = BadgeRaw.BadgeWithTextRaw::class, name = "badgeWithText"),
        JsonSubTypes.Type(
            value = BadgeRaw.BadgeWithImageRaw::class,
            name = "badgeWithImage"
        ),
    )
    sealed interface BadgeRaw {
        data class BadgeWithTextRaw(
            val textWithStyle: TextWithStyleRaw,
        ) : BadgeRaw

        data class BadgeWithImageRaw(
            val imageUrl: String,
        ) : BadgeRaw
    }
}

data class SpacerRaw(
    override val base: ComponentBaseRawProperties,
) : LeafRaw

data class ProgressBarRaw(
    override val base: ComponentBaseRawProperties,
) : LeafRaw

data class SwitchRaw(
    override val base: ComponentBaseRawProperties,
) : LeafRaw

data class ButtonRaw(
    val text: TextRaw,
    val enabled: Boolean,
    override val base: ComponentBaseRawProperties,
) : LeafRaw