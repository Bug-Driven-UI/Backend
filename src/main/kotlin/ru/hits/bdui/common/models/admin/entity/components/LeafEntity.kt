package ru.hits.bdui.common.models.admin.entity.components

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.admin.entity.components.additional.BorderEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.RegexEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.ShapeEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.InsetsEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.SizeEntity
import ru.hits.bdui.common.models.admin.entity.interactions.InteractionEntity
import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity
import ru.hits.bdui.common.models.admin.entity.styles.text.TextWithStyleEntity

sealed interface LeafEntity : ComponentEntity

data class TextEntity(
    val textWithStyle: TextWithStyleEntity,
    override val id: String,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : LeafEntity {
    override val type: String = "text"
}

data class InputEntity(
    val textWithStyle: TextWithStyleEntity,
    val mask: MaskEntity?,
    val regex: RegexEntity?,
    val rightIcon: ImageEntity?,
    val hint: HintEntity?,
    val placeholder: PlaceholderEntity?,
    override val id: String,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : LeafEntity {
    override val type: String = "textField"

    data class HintEntity(
        val textWithStyle: TextWithStyleEntity
    )

    data class PlaceholderEntity(
        val textWithStyle: TextWithStyleEntity
    )
}

enum class MaskEntity {
    @JsonProperty("phone")
    PHONE
}

data class ImageEntity(
    val imageUrl: String,
    val badge: BadgeEntity?,
    override val id: String,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : LeafEntity {
    override val type: String = "image"

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes(
        JsonSubTypes.Type(value = BadgeEntity.BadgeWithTextEntity::class, name = "badgeWithText"),
        JsonSubTypes.Type(
            value = BadgeEntity.BadgeWithImageEntity::class,
            name = "badgeWithImage"
        ),
    )
    sealed interface BadgeEntity {
        val type: String

        data class BadgeWithTextEntity(
            val textWithStyle: TextWithStyleEntity,
        ) : BadgeEntity {
            override val type: String = "badgeWithText"
        }

        data class BadgeWithImageEntity(
            val imageUrl: String,
        ) : BadgeEntity {
            override val type: String = "badgeWithImage"
        }
    }
}

data class SpacerEntity(
    override val id: String,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : LeafEntity {
    override val type: String = "spacer"
}

data class ProgressBarEntity(
    override val id: String,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : LeafEntity {
    override val type: String = "progressBar"
}

data class SwitchEntity(
    override val id: String,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : LeafEntity {
    override val type: String = "switch"
}

data class ButtonEntity(
    val textWithStyle: TextWithStyleEntity,
    val enabled: Boolean,
    override val id: String,
    override val interactions: List<InteractionEntity>,
    override val margins: InsetsEntity?,
    override val paddings: InsetsEntity?,
    override val width: SizeEntity,
    override val height: SizeEntity,
    override val backgroundColor: ColorStyleEntity?,
    override val border: BorderEntity?,
    override val shape: ShapeEntity?,
) : LeafEntity {
    override val type: String = "button"
}