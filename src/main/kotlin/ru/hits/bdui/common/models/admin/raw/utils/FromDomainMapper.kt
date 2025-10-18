package ru.hits.bdui.common.models.admin.raw.utils

import ru.hits.bdui.common.models.admin.raw.components.BoxRaw
import ru.hits.bdui.common.models.admin.raw.components.ButtonRaw
import ru.hits.bdui.common.models.admin.raw.components.ColumnRaw
import ru.hits.bdui.common.models.admin.raw.components.ComponentBaseRawProperties
import ru.hits.bdui.common.models.admin.raw.components.ComponentRaw
import ru.hits.bdui.common.models.admin.raw.components.DynamicColumnRaw
import ru.hits.bdui.common.models.admin.raw.components.DynamicRowRaw
import ru.hits.bdui.common.models.admin.raw.components.ImageRaw
import ru.hits.bdui.common.models.admin.raw.components.InputRaw
import ru.hits.bdui.common.models.admin.raw.components.MaskRaw
import ru.hits.bdui.common.models.admin.raw.components.ProgressBarRaw
import ru.hits.bdui.common.models.admin.raw.components.RowRaw
import ru.hits.bdui.common.models.admin.raw.components.SpacerRaw
import ru.hits.bdui.common.models.admin.raw.components.StateDefinitionRaw
import ru.hits.bdui.common.models.admin.raw.components.StatefulComponentRaw
import ru.hits.bdui.common.models.admin.raw.components.SwitchRaw
import ru.hits.bdui.common.models.admin.raw.components.TextRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.BorderRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalAndVerticalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.HorizontalArrangementRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.RegexRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.ShapeRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.ShapeTypeRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.VerticalAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.VerticalArrangementRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.InsetsRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.SizeRaw
import ru.hits.bdui.common.models.admin.raw.interactions.InteractionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.InteractionTypeRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.ActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.CommandActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.NavigateBackActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.NavigateToActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.NavigateToBottomSheetActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.UpdateScreenActionRaw
import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw
import ru.hits.bdui.common.models.admin.raw.styles.text.TextAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.styles.text.TextStyleRaw
import ru.hits.bdui.common.models.admin.raw.styles.text.TextWithStyleRaw
import ru.hits.bdui.domain.screen.components.Box
import ru.hits.bdui.domain.screen.components.Button
import ru.hits.bdui.domain.screen.components.Column
import ru.hits.bdui.domain.screen.components.Component
import ru.hits.bdui.domain.screen.components.ComponentBaseProperties
import ru.hits.bdui.domain.screen.components.DynamicColumn
import ru.hits.bdui.domain.screen.components.DynamicRow
import ru.hits.bdui.domain.screen.components.Image
import ru.hits.bdui.domain.screen.components.Input
import ru.hits.bdui.domain.screen.components.Mask
import ru.hits.bdui.domain.screen.components.ProgressBar
import ru.hits.bdui.domain.screen.components.Row
import ru.hits.bdui.domain.screen.components.Spacer
import ru.hits.bdui.domain.screen.components.StateDefinition
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
import ru.hits.bdui.domain.screen.interactions.actions.UpdateScreenAction
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.text.TextAlignment
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle

fun Component.toRaw(): ComponentRaw =
    when (this) {
        is Text -> TextRaw(
            textWithStyle = this.textWithStyle.toRaw(),
            base = this.base.toRaw(),
        )

        is Input -> InputRaw(
            textWithStyle = this.textWithStyle.toRaw(),
            mask = this.mask?.let {
                when (it) {
                    Mask.PHONE -> MaskRaw.PHONE
                }
            },
            regex = this.regex?.let {
                when (it) {
                    Regex.EMAIL -> RegexRaw.EMAIL
                }
            },
            rightIcon = this.rightIcon?.toRaw() as ImageRaw?,
            hint = this.hint?.let {
                InputRaw.HintRaw(
                    it.textWithStyle.toRaw()
                )
            },
            placeholder = this.placeholder?.let {
                InputRaw.PlaceholderRaw(
                    it.textWithStyle.toRaw()
                )
            },
            base = this.base.toRaw(),
        )

        is Image -> ImageRaw(
            imageUrl = this.imageUrl.value as String,
            badge = this.badge?.toRaw(),
            base = this.base.toRaw(),
        )

        is Spacer -> SpacerRaw(
            base = this.base.toRaw(),
        )

        is ProgressBar -> ProgressBarRaw(
            base = this.base.toRaw(),
        )

        is Switch -> SwitchRaw(
            base = this.base.toRaw(),
        )

        is Button -> ButtonRaw(
            text = this.text.toRaw() as TextRaw,
            enabled = this.enabled,
            base = this.base.toRaw(),
        )

        is Column -> ColumnRaw(
            children = this.children.map { it.toRaw() },
            base = this.base.toRaw(),
            verticalArrangement = this.verticalArrangement?.toRaw(),
            horizontalAlignment = this.horizontalAlignment?.toRaw(),
        )

        is Row -> RowRaw(
            children = this.children.map { it.toRaw() },
            base = this.base.toRaw(),
            horizontalArrangement = this.horizontalArrangement?.toRaw(),
            verticalAlignment = this.verticalAlignment?.toRaw(),
            isScrollable = this.isScrollable,
        )

        is Box -> BoxRaw(
            children = this.children.map { it.toRaw() },
            base = this.base.toRaw(),
            contentAlignment = this.contentAlignment?.toRaw(),
        )

        is StatefulComponent -> StatefulComponentRaw(
            base = this.base.toRaw(),
            states = this.states.map { it.toRaw() },
        )

        is DynamicColumn -> DynamicColumnRaw(
            base = this.base.toRaw(),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplateName = this.itemTemplate.name.value,
            verticalArrangement = this.verticalArrangement?.toRaw(),
            horizontalAlignment = this.horizontalAlignment?.toRaw(),
        )

        is DynamicRow -> DynamicRowRaw(
            base = this.base.toRaw(),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplateName = this.itemTemplate.name.value,
            horizontalArrangement = this.horizontalArrangement?.toRaw(),
            verticalAlignment = this.verticalAlignment?.toRaw(),
        )
    }

private fun ComponentBaseProperties.toRaw(): ComponentBaseRawProperties =
    ComponentBaseRawProperties(
        id = this.id.value.value as String,
        interactions = this.interactions.map(Interaction::toRaw),
        margins = this.margins?.toRaw(),
        paddings = this.paddings?.toRaw(),
        width = this.width.toRaw(),
        height = this.height.toRaw(),
        backgroundColor = this.backgroundColor?.toRaw(),
        border = this.border?.toRaw(),
        shape = this.shape?.toRaw(),
    )

private fun Insets.toRaw(): InsetsRaw =
    InsetsRaw(top = this.top, start = this.start, bottom = this.bottom, end = this.end)

private fun Size.toRaw(): SizeRaw =
    when (this) {
        is Size.WrapContent -> SizeRaw.WrapContentRaw
        is Size.MatchParent -> SizeRaw.MatchParentRaw
        is Size.Fixed -> SizeRaw.FixedRaw(this.value)
        is Size.Weighted -> SizeRaw.WeightedRaw(this.fraction)
    }

private fun HorizontalAndVerticalAlignment.toRaw(): HorizontalAndVerticalAlignmentRaw =
    when (this) {
        is HorizontalAndVerticalAlignment.TopStart -> HorizontalAndVerticalAlignmentRaw.TopStartRaw
        is HorizontalAndVerticalAlignment.TopCenter -> HorizontalAndVerticalAlignmentRaw.TopCenterRaw
        is HorizontalAndVerticalAlignment.TopEnd -> HorizontalAndVerticalAlignmentRaw.TopEndRaw
        is HorizontalAndVerticalAlignment.CenterStart -> HorizontalAndVerticalAlignmentRaw.CenterStartRaw
        is HorizontalAndVerticalAlignment.Center -> HorizontalAndVerticalAlignmentRaw.CenterRaw
        is HorizontalAndVerticalAlignment.CenterEnd -> HorizontalAndVerticalAlignmentRaw.CenterEndRaw
        is HorizontalAndVerticalAlignment.BottomStart -> HorizontalAndVerticalAlignmentRaw.BottomStartRaw
        is HorizontalAndVerticalAlignment.BottomCenter -> HorizontalAndVerticalAlignmentRaw.BottomCenterRaw
        is HorizontalAndVerticalAlignment.BottomEnd -> HorizontalAndVerticalAlignmentRaw.BottomEndRaw
    }

private fun HorizontalAlignment.toRaw(): HorizontalAlignmentRaw =
    when (this) {
        is HorizontalAlignment.End -> HorizontalAlignmentRaw.EndRaw
        is HorizontalAlignment.Start -> HorizontalAlignmentRaw.StartRaw
        is HorizontalAlignment.Center -> HorizontalAlignmentRaw.CenterRaw
    }

private fun VerticalAlignment.toRaw(): VerticalAlignmentRaw =
    when (this) {
        is VerticalAlignment.Top -> VerticalAlignmentRaw.TopRaw
        is VerticalAlignment.Bottom -> VerticalAlignmentRaw.BottomRaw
        is VerticalAlignment.Center -> VerticalAlignmentRaw.CenterRaw
    }

private fun HorizontalArrangement.toRaw(): HorizontalArrangementRaw =
    when (this) {
        is HorizontalArrangement.Start -> HorizontalArrangementRaw.StartRaw
        is HorizontalArrangement.End -> HorizontalArrangementRaw.EndRaw
        is HorizontalArrangement.Center -> HorizontalArrangementRaw.CenterRaw
        is HorizontalArrangement.SpaceAround -> HorizontalArrangementRaw.SpaceAroundRaw
        is HorizontalArrangement.SpaceEvenly -> HorizontalArrangementRaw.SpaceEvenlyRaw
        is HorizontalArrangement.SpaceBetween -> HorizontalArrangementRaw.SpaceBetweenRaw
    }

private fun VerticalArrangement.toRaw(): VerticalArrangementRaw =
    when (this) {
        is VerticalArrangement.Top -> VerticalArrangementRaw.TopRaw
        is VerticalArrangement.Bottom -> VerticalArrangementRaw.BottomRaw
        is VerticalArrangement.Center -> VerticalArrangementRaw.CenterRaw
        is VerticalArrangement.SpaceAround -> VerticalArrangementRaw.SpaceAroundRaw
        is VerticalArrangement.SpaceEvenly -> VerticalArrangementRaw.SpaceEvenlyRaw
        is VerticalArrangement.SpaceBetween -> VerticalArrangementRaw.SpaceBetweenRaw
    }

private fun Border.toRaw(): BorderRaw =
    BorderRaw(
        thickness = this.thickness,
        color = this.color.toRaw()
    )

private fun Shape.toRaw(): ShapeRaw =
    ShapeRaw(
        type = when (this.type) {
            ShapeType.ROUNDED_CORNERS -> ShapeTypeRaw.ROUNDED_CORNERS
        },
        topRight = this.topRight,
        topLeft = this.topLeft,
        bottomRight = this.bottomRight,
        bottomLeft = this.bottomLeft,
    )

private fun Interaction.toRaw(): InteractionRaw =
    InteractionRaw(
        type = when (this.type) {
            InteractionType.ON_CLICK -> InteractionTypeRaw.ON_CLICK
            InteractionType.ON_SHOW -> InteractionTypeRaw.ON_SHOW
        },
        actions = this.actions.map(Action::toRaw)
    )

private fun Action.toRaw(): ActionRaw =
    when (this) {
        is UpdateScreenAction -> UpdateScreenActionRaw(
            screenName = this.screenName.value,
            screenNavigationParams = this.screenNavigationParams.mapValues { it.value.value as String }
        )

        is CommandAction -> CommandActionRaw(
            name = this.name.value,
            params = this.params.mapValues { it.value.value as String }
        )

        is NavigateToAction -> NavigateToActionRaw(
            screenName = this.screenName.value,
            screenNavigationParams = this.screenNavigationParams.mapValues { it.value.value as String }
        )

        is NavigateBackAction -> NavigateBackActionRaw(
            updatePreviousScreen = this.updatePreviousScreen,
        )

        is NavigateToBottomSheetAction -> NavigateToBottomSheetActionRaw(
            screenName = this.screenName.value,
            screenNavigationParams = this.screenNavigationParams.mapValues { it.value.value as String }
        )
    }

private fun Image.Badge.toRaw(): ImageRaw.BadgeRaw =
    when (this) {
        is Image.Badge.BadgeWithImage -> ImageRaw.BadgeRaw.BadgeWithImageRaw(
            imageUrl = this.imageUrl.value as String
        )

        is Image.Badge.BadgeWithText -> ImageRaw.BadgeRaw.BadgeWithTextRaw(
            textWithStyle = textWithStyle.toRaw()
        )
    }

private fun StateDefinition.toRaw(): StateDefinitionRaw =
    StateDefinitionRaw(
        condition = this.condition.value,
        component = this.component.toRaw()
    )

private fun TextWithStyle.toRaw(): TextWithStyleRaw =
    TextWithStyleRaw(
        text = this.text.value as String,
        textStyle = this.textStyle.toRaw(),
        colorStyle = this.color.toRaw(),
        textAlignment = this.textAlignment?.let {
            when (it) {
                TextAlignment.END -> TextAlignmentRaw.END
                TextAlignment.START -> TextAlignmentRaw.START
                TextAlignment.CENTER -> TextAlignmentRaw.CENTER
            }
        }
    )

private fun TextStyle.toRaw(): TextStyleRaw =
    TextStyleRaw(
        token = this.token,
    )


private fun ColorStyle.toRaw(): ColorStyleRaw =
    ColorStyleRaw(
        token = this.token.value as String,
    )
