package ru.hits.bdui.common.models.admin.entity.components

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.admin.entity.components.additional.RegexEntity
import ru.hits.bdui.common.models.admin.entity.interactions.actions.ActionEntity
import ru.hits.bdui.common.models.admin.entity.styles.text.TextWithStyleEntity

sealed interface LeafEntity : ComponentEntity

data class TextEntity(
    val textWithStyle: TextWithStyleEntity,
    override val base: ComponentBaseEntityProperties
) : LeafEntity

data class InputEntity(
    val textWithStyle: TextWithStyleEntity,
    val mask: MaskEntity?,
    val regex: RegexEntity?,
    val rightIcon: ImageEntity?,
    val hint: HintEntity?,
    val placeholder: PlaceholderEntity?,
    val onValueChanged: List<ActionEntity>?,
    override val base: ComponentBaseEntityProperties
) : LeafEntity {
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
    override val base: ComponentBaseEntityProperties
) : LeafEntity {
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    @JsonSubTypes(
        JsonSubTypes.Type(value = BadgeEntity.BadgeWithTextEntity::class, name = "badgeWithText"),
        JsonSubTypes.Type(
            value = BadgeEntity.BadgeWithImageEntity::class,
            name = "badgeWithImage"
        ),
    )
    sealed interface BadgeEntity {
        data class BadgeWithTextEntity(
            val textWithStyle: TextWithStyleEntity,
        ) : BadgeEntity

        data class BadgeWithImageEntity(
            val imageUrl: String,
        ) : BadgeEntity
    }
}

data class SpacerEntity(
    override val base: ComponentBaseEntityProperties
) : LeafEntity

data class ProgressBarEntity(
    override val base: ComponentBaseEntityProperties
) : LeafEntity

data class SwitchEntity(
    override val base: ComponentBaseEntityProperties
) : LeafEntity

data class ButtonEntity(
    val text: TextEntity,
    val enabled: Boolean,
    override val base: ComponentBaseEntityProperties
) : LeafEntity