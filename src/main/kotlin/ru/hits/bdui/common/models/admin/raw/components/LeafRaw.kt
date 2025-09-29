package ru.hits.bdui.common.models.admin.raw.components

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.admin.raw.components.additional.BorderRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.RegexRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.ShapeRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.InsetsRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.SizeRaw
import ru.hits.bdui.common.models.admin.raw.interactions.InteractionRaw
import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw
import ru.hits.bdui.common.models.admin.raw.styles.text.TextWithStyleRaw

sealed interface LeafRaw : ComponentRaw

data class TextRaw(
    val textWithStyle: TextWithStyleRaw,
    override val id: String,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : LeafRaw {
    override val type: String = "text"
}

data class InputRaw(
    val textWithStyle: TextWithStyleRaw,
    val mask: MaskRaw?,
    val regex: RegexRaw?,
    val rightIcon: ImageRaw?,
    val hint: HintRaw?,
    val placeholder: PlaceholderRaw?,
    override val id: String,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : LeafRaw {
    override val type: String = "textField"

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
    override val id: String,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : LeafRaw {
    override val type: String = "image"

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes(
        JsonSubTypes.Type(value = BadgeRaw.BadgeWithTextRaw::class, name = "badgeWithText"),
        JsonSubTypes.Type(
            value = BadgeRaw.BadgeWithImageRaw::class,
            name = "badgeWithImage"
        ),
    )
    sealed interface BadgeRaw {
        val type: String

        data class BadgeWithTextRaw(
            val textWithStyle: TextWithStyleRaw,
        ) : BadgeRaw {
            override val type: String = "badgeWithText"
        }

        data class BadgeWithImageRaw(
            val imageUrl: String,
        ) : BadgeRaw {
            override val type: String = "badgeWithImage"
        }
    }
}

data class SpacerRaw(
    override val id: String,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : LeafRaw {
    override val type: String = "spacer"
}

data class ProgressBarRaw(
    override val id: String,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : LeafRaw {
    override val type: String = "progressBar"
}

data class SwitchRaw(
    override val id: String,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : LeafRaw {
    override val type: String = "switch"
}

data class ButtonRaw(
    val textWithStyle: TextWithStyleRaw,
    val enabled: Boolean,
    override val id: String,
    override val interactions: List<InteractionRaw>,
    override val margins: InsetsRaw?,
    override val paddings: InsetsRaw?,
    override val width: SizeRaw,
    override val height: SizeRaw,
    override val backgroundColor: ColorStyleRaw?,
    override val border: BorderRaw?,
    override val shape: ShapeRaw?,
) : LeafRaw {
    override val type: String = "button"
}