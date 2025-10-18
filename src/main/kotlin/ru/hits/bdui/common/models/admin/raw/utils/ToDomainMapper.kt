package ru.hits.bdui.common.models.admin.raw.utils

import ru.hits.bdui.common.components.validation.MappingContext
import ru.hits.bdui.common.models.admin.raw.components.BoxRaw
import ru.hits.bdui.common.models.admin.raw.components.ButtonRaw
import ru.hits.bdui.common.models.admin.raw.components.ColumnRaw
import ru.hits.bdui.common.models.admin.raw.components.ComponentBaseRawProperties
import ru.hits.bdui.common.models.admin.raw.components.ComponentRaw
import ru.hits.bdui.common.models.admin.raw.components.DynamicColumnRaw
import ru.hits.bdui.common.models.admin.raw.components.DynamicRowRaw
import ru.hits.bdui.common.models.admin.raw.components.ImageRaw
import ru.hits.bdui.common.models.admin.raw.components.InputRaw
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
import ru.hits.bdui.common.models.admin.raw.interactions.actions.SetLocalStateActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.SetLocalStateFromInputActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.UpdateScreenActionRaw
import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw
import ru.hits.bdui.common.models.admin.raw.styles.text.TextAlignmentRaw
import ru.hits.bdui.common.models.admin.raw.styles.text.TextStyleRaw
import ru.hits.bdui.common.models.admin.raw.styles.text.TextWithStyleRaw
import ru.hits.bdui.domain.CommandName
import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.ScreenName
import ru.hits.bdui.domain.ValueOrExpression
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
import ru.hits.bdui.domain.screen.interactions.actions.NavigateToBottomSheetAction
import ru.hits.bdui.domain.screen.interactions.actions.SetLocalStateAction
import ru.hits.bdui.domain.screen.interactions.actions.SetLocalStateFromInputAction
import ru.hits.bdui.domain.screen.interactions.actions.UpdateScreenAction
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.text.TextAlignment
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle
import ru.hits.bdui.engine.expression.ExpressionUtils
import ru.hits.bdui.engine.expression.ExpressionUtils.getValueOrExpression

fun ComponentRaw.toDomain(ctx: MappingContext): Component =
    when (this) {
        is TextRaw -> Text(
            textWithStyle = this.textWithStyle.toDomain(ctx),
            base = this.base.toDomain(ctx),
        )

        is InputRaw -> Input(
            textWithStyle = this.textWithStyle.toDomain(ctx),
            mask = this.mask?.let { Mask.valueOf(it.name) },
            regex = this.regex?.let { Regex.valueOf(it.name) },
            rightIcon = this.rightIcon?.let { it.toDomain(ctx) as Image? },
            hint = this.hint?.let { Input.Hint(it.textWithStyle.toDomain(ctx)) },
            placeholder = this.placeholder?.let { Input.Placeholder(it.textWithStyle.toDomain(ctx)) },
            base = this.base.toDomain(ctx),
            onValueChanged = this.onValueChanged?.map { it.toDomain() },
        )

        is ImageRaw -> Image(
            imageUrl = this.imageUrl.toDomainValueOrExpression(),
            badge = this.badge?.toDomain(ctx),
            base = this.base.toDomain(ctx),
        )

        is SpacerRaw -> Spacer(
            base = this.base.toDomain(ctx),
        )

        is ProgressBarRaw -> ProgressBar(
            base = this.base.toDomain(ctx),
        )

        is SwitchRaw -> Switch(
            base = this.base.toDomain(ctx),
        )

        is ButtonRaw -> Button(
            text = this.text.toDomain(ctx) as Text,
            enabled = this.enabled,
            base = this.base.toDomain(ctx),
        )

        is ColumnRaw -> Column(
            children = this.children.map { it.toDomain(ctx) },
            base = this.base.toDomain(ctx),
            verticalArrangement = this.verticalArrangement?.toDomain(),
            horizontalAlignment = this.horizontalAlignment?.toDomain(),
        )

        is RowRaw -> Row(
            children = this.children.map { it.toDomain(ctx) },
            base = this.base.toDomain(ctx),
            horizontalArrangement = this.horizontalArrangement?.toDomain(),
            verticalAlignment = this.verticalAlignment?.toDomain(),
            isScrollable = this.isScrollable,
        )

        is BoxRaw -> Box(
            children = this.children.map { it.toDomain(ctx) },
            base = this.base.toDomain(ctx),
            contentAlignment = this.contentAlignment?.toDomain(),
        )

        is StatefulComponentRaw -> StatefulComponent(
            base = this.base.toDomain(ctx),
            states = this.states.map { it.toDomain(ctx) },
        )

        is DynamicColumnRaw -> DynamicColumn(
            base = this.base.toDomain(ctx),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplate = ctx.template(this.itemTemplateName),
            verticalArrangement = this.verticalArrangement?.toDomain(),
            horizontalAlignment = this.horizontalAlignment?.toDomain(),
        )

        is DynamicRowRaw -> DynamicRow(
            base = this.base.toDomain(ctx),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplate = ctx.template(this.itemTemplateName),
            horizontalArrangement = this.horizontalArrangement?.toDomain(),
            verticalAlignment = this.verticalAlignment?.toDomain(),
        )
    }

private fun ComponentBaseRawProperties.toDomain(ctx: MappingContext): ComponentBaseProperties =
    ComponentBaseProperties(
        id = ComponentId(getValueOrExpression(this.id)),
        interactions = this.interactions.map(InteractionRaw::toDomain),
        margins = this.margins?.toDomain(),
        paddings = this.paddings?.toDomain(),
        width = this.width.toDomain(),
        height = this.height.toDomain(),
        backgroundColor = this.backgroundColor?.toDomain(ctx),
        border = this.border?.toDomain(ctx),
        shape = this.shape?.toDomain(),
    )

private fun InsetsRaw.toDomain(): Insets =
    Insets(top = this.top, start = this.start, bottom = this.bottom, end = this.end)

private fun SizeRaw.toDomain(): Size =
    when (this) {
        is SizeRaw.WrapContentRaw -> Size.WrapContent
        is SizeRaw.MatchParentRaw -> Size.MatchParent
        is SizeRaw.FixedRaw -> Size.Fixed(this.value)
        is SizeRaw.WeightedRaw -> Size.Weighted(this.fraction)
    }

private fun HorizontalAndVerticalAlignmentRaw.toDomain(): HorizontalAndVerticalAlignment =
    when (this) {
        is HorizontalAndVerticalAlignmentRaw.TopStartRaw -> HorizontalAndVerticalAlignment.TopStart
        is HorizontalAndVerticalAlignmentRaw.TopCenterRaw -> HorizontalAndVerticalAlignment.TopCenter
        is HorizontalAndVerticalAlignmentRaw.TopEndRaw -> HorizontalAndVerticalAlignment.TopEnd
        is HorizontalAndVerticalAlignmentRaw.CenterStartRaw -> HorizontalAndVerticalAlignment.CenterStart
        is HorizontalAndVerticalAlignmentRaw.CenterRaw -> HorizontalAndVerticalAlignment.Center
        is HorizontalAndVerticalAlignmentRaw.CenterEndRaw -> HorizontalAndVerticalAlignment.CenterEnd
        is HorizontalAndVerticalAlignmentRaw.BottomStartRaw -> HorizontalAndVerticalAlignment.BottomStart
        is HorizontalAndVerticalAlignmentRaw.BottomCenterRaw -> HorizontalAndVerticalAlignment.BottomCenter
        is HorizontalAndVerticalAlignmentRaw.BottomEndRaw -> HorizontalAndVerticalAlignment.BottomEnd
    }

private fun HorizontalAlignmentRaw.toDomain(): HorizontalAlignment =
    when (this) {
        is HorizontalAlignmentRaw.EndRaw -> HorizontalAlignment.End
        is HorizontalAlignmentRaw.StartRaw -> HorizontalAlignment.Start
        is HorizontalAlignmentRaw.CenterRaw -> HorizontalAlignment.Center
    }

private fun VerticalAlignmentRaw.toDomain(): VerticalAlignment =
    when (this) {
        is VerticalAlignmentRaw.TopRaw -> VerticalAlignment.Top
        is VerticalAlignmentRaw.BottomRaw -> VerticalAlignment.Bottom
        is VerticalAlignmentRaw.CenterRaw -> VerticalAlignment.Center
    }

private fun HorizontalArrangementRaw.toDomain(): HorizontalArrangement =
    when (this) {
        is HorizontalArrangementRaw.StartRaw -> HorizontalArrangement.Start
        is HorizontalArrangementRaw.EndRaw -> HorizontalArrangement.End
        is HorizontalArrangementRaw.CenterRaw -> HorizontalArrangement.Center
        is HorizontalArrangementRaw.SpaceAroundRaw -> HorizontalArrangement.SpaceAround
        is HorizontalArrangementRaw.SpaceEvenlyRaw -> HorizontalArrangement.SpaceEvenly
        is HorizontalArrangementRaw.SpaceBetweenRaw -> HorizontalArrangement.SpaceBetween
    }

private fun VerticalArrangementRaw.toDomain(): VerticalArrangement =
    when (this) {
        is VerticalArrangementRaw.TopRaw -> VerticalArrangement.Top
        is VerticalArrangementRaw.BottomRaw -> VerticalArrangement.Bottom
        is VerticalArrangementRaw.CenterRaw -> VerticalArrangement.Center
        is VerticalArrangementRaw.SpaceAroundRaw -> VerticalArrangement.SpaceAround
        is VerticalArrangementRaw.SpaceEvenlyRaw -> VerticalArrangement.SpaceEvenly
        is VerticalArrangementRaw.SpaceBetweenRaw -> VerticalArrangement.SpaceBetween
    }

private fun BorderRaw.toDomain(ctx: MappingContext): Border =
    Border(
        thickness = this.thickness,
        color = this.color.toDomain(ctx)
    )

private fun ShapeRaw.toDomain(): Shape =
    Shape(
        type = when (this.type) {
            ShapeTypeRaw.ROUNDED_CORNERS -> ShapeType.ROUNDED_CORNERS
        },
        topRight = this.topRight,
        topLeft = this.topLeft,
        bottomRight = this.bottomRight,
        bottomLeft = this.bottomLeft,
    )

private fun InteractionRaw.toDomain(): Interaction =
    Interaction(
        type = when (this.type) {
            InteractionTypeRaw.ON_CLICK -> InteractionType.ON_CLICK
            InteractionTypeRaw.ON_SHOW -> InteractionType.ON_SHOW
        },
        actions = this.actions.map(ActionRaw::toDomain)
    )

private fun ActionRaw.toDomain(): Action =
    when (this) {
        is UpdateScreenActionRaw -> UpdateScreenAction(
            screenName = ScreenName(this.screenName),
            screenNavigationParams = this.screenNavigationParams.mapValues { getValueOrExpression(it.value) }
        )

        is CommandActionRaw -> CommandAction(
            name = CommandName(this.name),
            params = this.params.mapValues { getValueOrExpression(it.value) }
        )

        is NavigateToActionRaw -> NavigateToAction(
            screenName = ScreenName(this.screenName),
            screenNavigationParams = this.screenNavigationParams.mapValues { getValueOrExpression(it.value) }
        )

        is NavigateBackActionRaw -> NavigateBackAction(
            updatePreviousScreen = this.updatePreviousScreen,
        )

        is NavigateToBottomSheetActionRaw -> NavigateToBottomSheetAction(
            screenName = ScreenName(this.screenName),
            screenNavigationParams = this.screenNavigationParams.mapValues { getValueOrExpression(it.value) }
        )

        is SetLocalStateActionRaw -> SetLocalStateAction(
            target = getValueOrExpression(this.target),
            value = getValueOrExpression(this.value),
        )

        is SetLocalStateFromInputActionRaw -> SetLocalStateFromInputAction(
            target = getValueOrExpression(this.target),
        )
    }

private fun Any.toDomainValueOrExpression(): ValueOrExpression =
    getValueOrExpression(this)

private fun ImageRaw.BadgeRaw.toDomain(ctx: MappingContext): Image.Badge =
    when (this) {
        is ImageRaw.BadgeRaw.BadgeWithImageRaw -> Image.Badge.BadgeWithImage(
            imageUrl = getValueOrExpression(this.imageUrl)
        )

        is ImageRaw.BadgeRaw.BadgeWithTextRaw -> Image.Badge.BadgeWithText(
            textWithStyle = textWithStyle.toDomain(ctx)
        )
    }

private fun StateDefinitionRaw.toDomain(ctx: MappingContext): StateDefinition =
    StateDefinition(
        condition = ExpressionUtils.getExpressionOrThrow(this.condition),
        component = this.component.toDomain(ctx)
    )

private fun TextWithStyleRaw.toDomain(ctx: MappingContext): TextWithStyle =
    TextWithStyle(
        text = getValueOrExpression(this.text),
        textStyle = this.textStyle.toDomain(ctx),
        color = this.colorStyle.toDomain(ctx),
        textAlignment = this.textAlignment?.let {
            when (it) {
                TextAlignmentRaw.CENTER -> TextAlignment.CENTER
                TextAlignmentRaw.START -> TextAlignment.START
                TextAlignmentRaw.END -> TextAlignment.END
            }
        }
    )

private fun TextStyleRaw.toDomain(ctx: MappingContext): TextStyle {
    val textStyle = ctx.text(this.token)

    return TextStyle(
        token = textStyle.token,
        decoration = textStyle.decoration,
        weight = textStyle.weight,
        size = textStyle.size,
        lineHeight = textStyle.lineHeight,
    )
}


private fun ColorStyleRaw.toDomain(ctx: MappingContext): ColorStyle {
    val color = ctx.color(this.token)

    return ColorStyle(
        token = color.token,
        color = color.color
    )
}
