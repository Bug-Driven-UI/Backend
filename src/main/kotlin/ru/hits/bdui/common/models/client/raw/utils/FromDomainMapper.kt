package ru.hits.bdui.common.models.client.raw.utils

import ru.hits.bdui.common.models.client.raw.components.BoxRawRendered
import ru.hits.bdui.common.models.client.raw.components.ButtonRawRendered
import ru.hits.bdui.common.models.client.raw.components.ColumnRawRendered
import ru.hits.bdui.common.models.client.raw.components.ImageRawRendered
import ru.hits.bdui.common.models.client.raw.components.InputRawRendered
import ru.hits.bdui.common.models.client.raw.components.ProgressBarRawRendered
import ru.hits.bdui.common.models.client.raw.components.RenderedComponentBaseRawProperties
import ru.hits.bdui.common.models.client.raw.components.RenderedComponentRaw
import ru.hits.bdui.common.models.client.raw.components.RenderedMaskRaw
import ru.hits.bdui.common.models.client.raw.components.RowRawRendered
import ru.hits.bdui.common.models.client.raw.components.SpacerRawRendered
import ru.hits.bdui.common.models.client.raw.components.SwitchRawRendered
import ru.hits.bdui.common.models.client.raw.components.TextRawRendered
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedBorderRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedRegexRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedShapeRaw
import ru.hits.bdui.common.models.client.raw.components.additional.ShapeTypeRaw
import ru.hits.bdui.common.models.client.raw.components.properties.RenderedInsetsRaw
import ru.hits.bdui.common.models.client.raw.components.properties.RenderedSizeRaw
import ru.hits.bdui.common.models.client.raw.interactions.RenderedInteractionRaw
import ru.hits.bdui.common.models.client.raw.interactions.RenderedInteractionTypeRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.CommandRenderedActionRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.NavigateBackRenderedActionRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.NavigateToRenderedActionRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.RenderedActionRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.UpdateScreenRenderedActionRaw
import ru.hits.bdui.common.models.client.raw.styles.color.RenderedColorStyleRaw
import ru.hits.bdui.common.models.client.raw.styles.text.RenderedTextStyleRaw
import ru.hits.bdui.common.models.client.raw.styles.text.RenderedTextWithStyleRaw
import ru.hits.bdui.common.models.client.raw.styles.text.TextDecorationRaw
import ru.hits.bdui.domain.screen.components.Box
import ru.hits.bdui.domain.screen.components.Button
import ru.hits.bdui.domain.screen.components.Column
import ru.hits.bdui.domain.screen.components.Component
import ru.hits.bdui.domain.screen.components.DynamicColumn
import ru.hits.bdui.domain.screen.components.DynamicRow
import ru.hits.bdui.domain.screen.components.Image
import ru.hits.bdui.domain.screen.components.Input
import ru.hits.bdui.domain.screen.components.Mask
import ru.hits.bdui.domain.screen.components.ProgressBar
import ru.hits.bdui.domain.screen.components.Row
import ru.hits.bdui.domain.screen.components.Spacer
import ru.hits.bdui.domain.screen.components.StatefulComponent
import ru.hits.bdui.domain.screen.components.Switch
import ru.hits.bdui.domain.screen.components.Text
import ru.hits.bdui.domain.screen.components.additional.Border
import ru.hits.bdui.domain.screen.components.additional.Regex
import ru.hits.bdui.domain.screen.components.additional.Shape
import ru.hits.bdui.domain.screen.components.additional.ShapeType
import ru.hits.bdui.domain.screen.components.properties.Insets
import ru.hits.bdui.domain.screen.components.properties.Size
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.interactions.InteractionType
import ru.hits.bdui.domain.screen.interactions.actions.Action
import ru.hits.bdui.domain.screen.interactions.actions.CommandAction
import ru.hits.bdui.domain.screen.interactions.actions.NavigateBackAction
import ru.hits.bdui.domain.screen.interactions.actions.NavigateToAction
import ru.hits.bdui.domain.screen.interactions.actions.UpdateScreenAction
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.text.TextDecoration
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle
import ru.hits.bdui.utils.DomainComponentHasher

fun Component.toRaw(): RenderedComponentRaw =
    when (this) {
        is Text -> TextRawRendered(
            textWithStyle = this.textWithStyle.toRaw(),
            base = this.toRawBaseProperties(),
        )

        is Input -> InputRawRendered(
            textWithStyle = this.textWithStyle.toRaw(),
            mask = this.mask?.let {
                when (it) {
                    Mask.PHONE -> RenderedMaskRaw.PHONE
                }
            },
            regex = this.regex?.let {
                when (it) {
                    Regex.EMAIL -> RenderedRegexRaw.EMAIL
                }
            },
            rightIcon = this.rightIcon?.toRaw() as ImageRawRendered?,
            hint = this.hint?.let {
                InputRawRendered.RenderedHintRaw(
                    it.textWithStyle.toRaw()
                )
            },
            placeholder = this.placeholder?.let {
                InputRawRendered.RenderedPlaceholderRaw(
                    it.textWithStyle.toRaw()
                )
            },
            base = this.toRawBaseProperties(),
        )

        is Image -> ImageRawRendered(
            imageUrl = this.imageUrl.value as String,
            badge = this.badge?.toRaw(),
            base = this.toRawBaseProperties(),
        )

        is Spacer -> SpacerRawRendered(
            base = this.toRawBaseProperties(),
        )

        is ProgressBar -> ProgressBarRawRendered(
            base = this.toRawBaseProperties(),
        )

        is Switch -> SwitchRawRendered(
            base = this.toRawBaseProperties(),
        )

        is Button -> ButtonRawRendered(
            textWithStyle = this.textWithStyle.toRaw(),
            enabled = this.enabled,
            base = this.toRawBaseProperties(),
        )

        is Column -> ColumnRawRendered(
            children = this.children.map { it.toRaw() },
            base = this.toRawBaseProperties(),
        )

        is Row -> RowRawRendered(
            children = this.children.map { it.toRaw() },
            base = this.toRawBaseProperties(),
        )

        is Box -> BoxRawRendered(
            children = this.children.map { it.toRaw() },
            base = this.toRawBaseProperties(),
        )

        is StatefulComponent, is DynamicColumn, is DynamicRow ->
            throw IllegalStateException("StatefulComponent, DynamicColumn and DynamicRow cannot be mapped to client models.")
    }

private fun Component.toRawBaseProperties(): RenderedComponentBaseRawProperties =
    RenderedComponentBaseRawProperties(
        id = this.base.id.value.value as String,
        hash = DomainComponentHasher.calculate(this),
        interactions = this.base.interactions.map(Interaction::toRaw),
        margins = this.base.margins?.toRaw(),
        paddings = this.base.paddings?.toRaw(),
        width = this.base.width.toRaw(),
        height = this.base.height.toRaw(),
        backgroundColor = this.base.backgroundColor?.toRaw(),
        border = this.base.border?.toRaw(),
        shape = this.base.shape?.toRaw(),
    )

private fun Insets.toRaw(): RenderedInsetsRaw =
    RenderedInsetsRaw(top = this.top, start = this.start, bottom = this.bottom, end = this.end)

private fun Size.toRaw(): RenderedSizeRaw =
    when (this) {
        is Size.WrapContent -> RenderedSizeRaw.WrapContentRawRendered()
        is Size.MatchParent -> RenderedSizeRaw.MatchParentRawRendered()
        is Size.Fixed -> RenderedSizeRaw.FixedRawRendered(this.value)
        is Size.Weighted -> RenderedSizeRaw.WeightedRawRendered(this.fraction)
    }

private fun Border.toRaw(): RenderedBorderRaw =
    RenderedBorderRaw(
        thickness = this.thickness,
        color = this.color.toRaw()
    )

private fun Shape.toRaw(): RenderedShapeRaw =
    RenderedShapeRaw(
        type = when (this.type) {
            ShapeType.ROUNDED_CORNERS -> ShapeTypeRaw.ROUNDED_CORNERS
        },
        topRight = this.topRight,
        topLeft = this.topLeft,
        bottomRight = this.bottomRight,
        bottomLeft = this.bottomLeft,
    )

private fun Interaction.toRaw(): RenderedInteractionRaw =
    RenderedInteractionRaw(
        type = when (this.type) {
            InteractionType.ON_CLICK -> RenderedInteractionTypeRaw.ON_CLICK
            InteractionType.ON_SHOW -> RenderedInteractionTypeRaw.ON_SHOW
        },
        actions = this.actions.map(Action::toRaw)
    )

private fun Action.toRaw(): RenderedActionRaw =
    when (this) {
        is UpdateScreenAction -> UpdateScreenRenderedActionRaw(
            screenName = this.screenName.value,
            screenNavigationParams = this.screenNavigationParams.mapValues { it.value.value as String }
        )

        is CommandAction -> CommandRenderedActionRaw(
            name = this.name.value,
            params = this.params.mapValues { it.value.value as String }
        )

        is NavigateToAction -> NavigateToRenderedActionRaw(
            screenName = this.screenName.value,
            screenNavigationParams = this.screenNavigationParams.mapValues { it.value.value as String }
        )

        is NavigateBackAction -> NavigateBackRenderedActionRaw()
    }

private fun Image.Badge.toRaw(): ImageRawRendered.RenderedBadgeRaw =
    when (this) {
        is Image.Badge.BadgeWithImage -> ImageRawRendered.RenderedBadgeRaw.BadgeWithImageRaw(
            imageUrl = this.imageUrl.value as String
        )

        is Image.Badge.BadgeWithText -> ImageRawRendered.RenderedBadgeRaw.BadgeWithTextRaw(
            textWithStyle = textWithStyle.toRaw()
        )
    }

private fun TextWithStyle.toRaw(): RenderedTextWithStyleRaw =
    RenderedTextWithStyleRaw(
        text = this.text.value as String,
        textStyle = this.textStyle.toRaw(),
        colorStyle = this.color.toRaw()
    )

private fun TextStyle.toRaw(): RenderedTextStyleRaw =
    RenderedTextStyleRaw(
        decoration = when (this.decoration) {
            TextDecoration.REGULAR -> TextDecorationRaw.REGULAR
            TextDecoration.BOLD -> TextDecorationRaw.BOLD
            TextDecoration.ITALIC -> TextDecorationRaw.ITALIC
            TextDecoration.UNDERLINE -> TextDecorationRaw.UNDERLINE
            TextDecoration.STRIKETHROUGH -> TextDecorationRaw.STRIKETHROUGH
            TextDecoration.OVERLINE -> TextDecorationRaw.OVERLINE
            TextDecoration.STRIKETHROUGH_RED -> TextDecorationRaw.STRIKETHROUGH_RED
            null -> null
        },
        weight = this.weight,
        size = this.size,
        lineHeight = this.lineHeight,
    )


private fun ColorStyle.toRaw(): RenderedColorStyleRaw =
    RenderedColorStyleRaw(
        hex = this.color.value as String,
    )
