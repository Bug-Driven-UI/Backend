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
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedHorizontalAlignmentRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedHorizontalAndVerticalAlignmentRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedHorizontalArrangementRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedRegexRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedShapeRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedVerticalAlignmentRaw
import ru.hits.bdui.common.models.client.raw.components.additional.RenderedVerticalArrangementRaw
import ru.hits.bdui.common.models.client.raw.components.additional.ShapeTypeRaw
import ru.hits.bdui.common.models.client.raw.components.properties.RenderedInsetsRaw
import ru.hits.bdui.common.models.client.raw.components.properties.RenderedSizeRaw
import ru.hits.bdui.common.models.client.raw.interactions.RenderedInteractionRaw
import ru.hits.bdui.common.models.client.raw.interactions.RenderedInteractionTypeRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.CommandRenderedActionRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.NavigateBackRenderedActionRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.NavigateToBottomSheetRenderedActionRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.NavigateToRenderedActionRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.RenderedActionRaw
import ru.hits.bdui.common.models.client.raw.interactions.actions.UpdateScreenRenderedActionRaw
import ru.hits.bdui.common.models.client.raw.styles.color.RenderedColorStyleRaw
import ru.hits.bdui.common.models.client.raw.styles.text.RenderedTextAlignment
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
import ru.hits.bdui.domain.screen.components.additional.HorizontalAlignment
import ru.hits.bdui.domain.screen.components.additional.HorizontalAndVerticalAlignment
import ru.hits.bdui.domain.screen.components.additional.HorizontalArrangement
import ru.hits.bdui.domain.screen.components.additional.Regex
import ru.hits.bdui.domain.screen.components.additional.Shape
import ru.hits.bdui.domain.screen.components.additional.ShapeType
import ru.hits.bdui.domain.screen.components.additional.VerticalAlignment
import ru.hits.bdui.domain.screen.components.additional.VerticalArrangement
import ru.hits.bdui.domain.screen.components.properties.Insets
import ru.hits.bdui.domain.screen.components.properties.Size
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.interactions.InteractionType
import ru.hits.bdui.domain.screen.interactions.actions.Action
import ru.hits.bdui.domain.screen.interactions.actions.CommandAction
import ru.hits.bdui.domain.screen.interactions.actions.NavigateBackAction
import ru.hits.bdui.domain.screen.interactions.actions.NavigateToAction
import ru.hits.bdui.domain.screen.interactions.actions.NavigateToBottomSheetAction
import ru.hits.bdui.domain.screen.interactions.actions.UpdateScreenAction
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.text.TextAlignment
import ru.hits.bdui.domain.screen.styles.text.TextDecoration
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle
import ru.hits.bdui.utils.DomainComponentHasher

fun Component.toRendered(): RenderedComponentRaw =
    when (this) {
        is Text -> TextRawRendered(
            textWithStyle = this.textWithStyle.toRendered(),
            base = this.toRenderedBaseProperties(),
        )

        is Input -> InputRawRendered(
            textWithStyle = this.textWithStyle.toRendered(),
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
            rightIcon = this.rightIcon?.toRendered() as ImageRawRendered?,
            hint = this.hint?.let {
                InputRawRendered.RenderedHintRaw(
                    it.textWithStyle.toRendered()
                )
            },
            placeholder = this.placeholder?.let {
                InputRawRendered.RenderedPlaceholderRaw(
                    it.textWithStyle.toRendered()
                )
            },
            base = this.toRenderedBaseProperties(),
        )

        is Image -> ImageRawRendered(
            imageUrl = this.imageUrl.value as String,
            badge = this.badge?.toRendered(),
            base = this.toRenderedBaseProperties(),
        )

        is Spacer -> SpacerRawRendered(
            base = this.toRenderedBaseProperties(),
        )

        is ProgressBar -> ProgressBarRawRendered(
            base = this.toRenderedBaseProperties(),
        )

        is Switch -> SwitchRawRendered(
            base = this.toRenderedBaseProperties(),
        )

        is Button -> ButtonRawRendered(
            text = this.text.toRendered() as TextRawRendered,
            enabled = this.enabled,
            base = this.toRenderedBaseProperties(),
        )

        is Column -> ColumnRawRendered(
            children = this.children.map { it.toRendered() },
            base = this.toRenderedBaseProperties(),
            verticalArrangement = this.verticalArrangement?.toRendered(),
            horizontalAlignment = this.horizontalAlignment?.toRendered()
        )

        is Row -> RowRawRendered(
            children = this.children.map { it.toRendered() },
            base = this.toRenderedBaseProperties(),
            horizontalArrangement = this.horizontalArrangement?.toRendered(),
            verticalAlignment = this.verticalAlignment?.toRendered(),
            isScrollable = this.isScrollable,
        )

        is Box -> BoxRawRendered(
            children = this.children.map { it.toRendered() },
            base = this.toRenderedBaseProperties(),
            contentAlignment = this.contentAlignment?.toRendered(),
        )

        is StatefulComponent, is DynamicColumn, is DynamicRow ->
            throw IllegalStateException("StatefulComponent, DynamicColumn and DynamicRow cannot be mapped to client models.")
    }

private fun Component.toRenderedBaseProperties(): RenderedComponentBaseRawProperties =
    RenderedComponentBaseRawProperties(
        id = this.base.id.value.value as String,
        hash = DomainComponentHasher.calculate(this),
        interactions = this.base.interactions.map(Interaction::toRendered),
        margins = this.base.margins?.toRendered(),
        paddings = this.base.paddings?.toRendered(),
        width = this.base.width.toRendered(),
        height = this.base.height.toRendered(),
        backgroundColor = this.base.backgroundColor?.toRendered(),
        border = this.base.border?.toRendered(),
        shape = this.base.shape?.toRendered(),
    )

private fun Insets.toRendered(): RenderedInsetsRaw =
    RenderedInsetsRaw(top = this.top, start = this.start, bottom = this.bottom, end = this.end)

private fun Size.toRendered(): RenderedSizeRaw =
    when (this) {
        is Size.WrapContent -> RenderedSizeRaw.WrapContentRawRendered
        is Size.MatchParent -> RenderedSizeRaw.MatchParentRawRendered
        is Size.Fixed -> RenderedSizeRaw.FixedRawRendered(this.value)
        is Size.Weighted -> RenderedSizeRaw.WeightedRawRendered(this.fraction)
    }

private fun HorizontalAndVerticalAlignment.toRendered(): RenderedHorizontalAndVerticalAlignmentRaw =
    when (this) {
        is HorizontalAndVerticalAlignment.TopStart -> RenderedHorizontalAndVerticalAlignmentRaw.RenderedTopStartRaw
        is HorizontalAndVerticalAlignment.TopCenter -> RenderedHorizontalAndVerticalAlignmentRaw.RenderedTopCenterRaw
        is HorizontalAndVerticalAlignment.TopEnd -> RenderedHorizontalAndVerticalAlignmentRaw.RenderedTopEndRaw
        is HorizontalAndVerticalAlignment.CenterStart -> RenderedHorizontalAndVerticalAlignmentRaw.RenderedCenterStartRaw
        is HorizontalAndVerticalAlignment.Center -> RenderedHorizontalAndVerticalAlignmentRaw.RenderedCenterRaw
        is HorizontalAndVerticalAlignment.CenterEnd -> RenderedHorizontalAndVerticalAlignmentRaw.RenderedCenterEndRaw
        is HorizontalAndVerticalAlignment.BottomStart -> RenderedHorizontalAndVerticalAlignmentRaw.RenderedBottomStartRaw
        is HorizontalAndVerticalAlignment.BottomCenter -> RenderedHorizontalAndVerticalAlignmentRaw.RenderedBottomCenterRaw
        is HorizontalAndVerticalAlignment.BottomEnd -> RenderedHorizontalAndVerticalAlignmentRaw.RenderedBottomEndRaw
    }

private fun HorizontalAlignment.toRendered(): RenderedHorizontalAlignmentRaw =
    when (this) {
        is HorizontalAlignment.End -> RenderedHorizontalAlignmentRaw.RenderedEndRaw
        is HorizontalAlignment.Start -> RenderedHorizontalAlignmentRaw.RenderedStartRaw
        is HorizontalAlignment.Center -> RenderedHorizontalAlignmentRaw.RenderedCenterRaw
    }

private fun VerticalAlignment.toRendered(): RenderedVerticalAlignmentRaw =
    when (this) {
        is VerticalAlignment.Top -> RenderedVerticalAlignmentRaw.RenderedTopRaw
        is VerticalAlignment.Bottom -> RenderedVerticalAlignmentRaw.RenderedBottomRaw
        is VerticalAlignment.Center -> RenderedVerticalAlignmentRaw.RenderedCenterRaw
    }

private fun HorizontalArrangement.toRendered(): RenderedHorizontalArrangementRaw =
    when (this) {
        is HorizontalArrangement.Start -> RenderedHorizontalArrangementRaw.RenderedStartRaw
        is HorizontalArrangement.End -> RenderedHorizontalArrangementRaw.RenderedEndRaw
        is HorizontalArrangement.Center -> RenderedHorizontalArrangementRaw.RenderedCenterRaw
        is HorizontalArrangement.SpaceAround -> RenderedHorizontalArrangementRaw.RenderedSpaceAroundRaw
        is HorizontalArrangement.SpaceEvenly -> RenderedHorizontalArrangementRaw.RenderedSpaceEvenlyRaw
        is HorizontalArrangement.SpaceBetween -> RenderedHorizontalArrangementRaw.RenderedSpaceBetweenRaw
    }

private fun VerticalArrangement.toRendered(): RenderedVerticalArrangementRaw =
    when (this) {
        is VerticalArrangement.Top -> RenderedVerticalArrangementRaw.RenderedTopRaw
        is VerticalArrangement.Bottom -> RenderedVerticalArrangementRaw.RenderedBottomRaw
        is VerticalArrangement.Center -> RenderedVerticalArrangementRaw.RenderedCenterRaw
        is VerticalArrangement.SpaceAround -> RenderedVerticalArrangementRaw.RenderedSpaceAroundRaw
        is VerticalArrangement.SpaceEvenly -> RenderedVerticalArrangementRaw.RenderedSpaceEvenlyRaw
        is VerticalArrangement.SpaceBetween -> RenderedVerticalArrangementRaw.RenderedSpaceBetweenRaw
    }

private fun Border.toRendered(): RenderedBorderRaw =
    RenderedBorderRaw(
        thickness = this.thickness,
        color = this.color.toRendered()
    )

private fun Shape.toRendered(): RenderedShapeRaw =
    RenderedShapeRaw(
        type = when (this.type) {
            ShapeType.ROUNDED_CORNERS -> ShapeTypeRaw.ROUNDED_CORNERS
        },
        topRight = this.topRight,
        topLeft = this.topLeft,
        bottomRight = this.bottomRight,
        bottomLeft = this.bottomLeft,
    )

private fun Interaction.toRendered(): RenderedInteractionRaw =
    RenderedInteractionRaw(
        type = when (this.type) {
            InteractionType.ON_CLICK -> RenderedInteractionTypeRaw.ON_CLICK
            InteractionType.ON_SHOW -> RenderedInteractionTypeRaw.ON_SHOW
        },
        actions = this.actions.map(Action::toRendered)
    )

private fun Action.toRendered(): RenderedActionRaw =
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

        is NavigateBackAction -> NavigateBackRenderedActionRaw(
            updatePreviousScreen = this.updatePreviousScreen
        )

        is NavigateToBottomSheetAction -> NavigateToBottomSheetRenderedActionRaw(
            screenName = this.screenName.value,
            screenNavigationParams = this.screenNavigationParams.mapValues { it.value.value as String }
        )
    }

private fun Image.Badge.toRendered(): ImageRawRendered.RenderedBadgeRaw =
    when (this) {
        is Image.Badge.BadgeWithImage -> ImageRawRendered.RenderedBadgeRaw.BadgeWithImageRaw(
            imageUrl = this.imageUrl.value as String
        )

        is Image.Badge.BadgeWithText -> ImageRawRendered.RenderedBadgeRaw.BadgeWithTextRaw(
            textWithStyle = textWithStyle.toRendered()
        )
    }

private fun TextWithStyle.toRendered(): RenderedTextWithStyleRaw =
    RenderedTextWithStyleRaw(
        text = this.text.value as String,
        textStyle = this.textStyle.toRendered(),
        colorStyle = this.color.toRendered(),
        textAlignment = this.textAlignment?.let {
            when (it) {
                TextAlignment.START -> RenderedTextAlignment.START
                TextAlignment.CENTER -> RenderedTextAlignment.CENTER
                TextAlignment.END -> RenderedTextAlignment.END
            }
        }
    )

private fun TextStyle.toRendered(): RenderedTextStyleRaw =
    RenderedTextStyleRaw(
        decoration = when (this.decoration) {
            TextDecoration.REGULAR -> TextDecorationRaw.REGULAR
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


private fun ColorStyle.toRendered(): RenderedColorStyleRaw =
    RenderedColorStyleRaw(
        hex = this.color.value as String,
    )
