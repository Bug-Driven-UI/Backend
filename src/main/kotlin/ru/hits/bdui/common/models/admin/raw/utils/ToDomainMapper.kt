package ru.hits.bdui.common.models.admin.raw.utils

import ru.hits.bdui.common.components.validation.MappingContext
import ru.hits.bdui.common.models.admin.raw.components.BoxRaw
import ru.hits.bdui.common.models.admin.raw.components.ButtonRaw
import ru.hits.bdui.common.models.admin.raw.components.ColumnRaw
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
import ru.hits.bdui.common.models.admin.raw.components.additional.ShapeRaw
import ru.hits.bdui.common.models.admin.raw.components.additional.ShapeTypeRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.InsetsRaw
import ru.hits.bdui.common.models.admin.raw.components.properties.SizeRaw
import ru.hits.bdui.common.models.admin.raw.interactions.InteractionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.InteractionTypeRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.ActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.CommandActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.NavigateBackActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.NavigateToActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.UpdateScreenActionRaw
import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw
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
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle
import ru.hits.bdui.core.expression.ExpressionUtils

fun ComponentRaw.toDomain(ctx: MappingContext): Component =
    when (this) {
        is TextRaw -> Text(
            textWithStyle = this.textWithStyle.toDomain(ctx),
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is InputRaw -> Input(
            textWithStyle = this.textWithStyle.toDomain(ctx),
            mask = this.mask?.let { Mask.valueOf(it.name) },
            regex = this.regex?.let { Regex.valueOf(it.name) },
            rightIcon = this.rightIcon?.let { it.toDomain(ctx) as Image? },
            hint = this.hint?.let { Input.Hint(it.textWithStyle.toDomain(ctx)) },
            placeholder = this.placeholder?.let { Input.Placeholder(it.textWithStyle.toDomain(ctx)) },
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is ImageRaw -> Image(
            imageUrl = this.imageUrl.toDomainValueOrExpression(),
            badge = this.badge?.toDomain(ctx),
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is SpacerRaw -> Spacer(
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is ProgressBarRaw -> ProgressBar(
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is SwitchRaw -> Switch(
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is ButtonRaw -> Button(
            textWithStyle = this.textWithStyle.toDomain(ctx),
            enabled = this.enabled,
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is ColumnRaw -> Column(
            children = this.children.map { it.toDomain(ctx) },
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is RowRaw -> Row(
            children = this.children.map { it.toDomain(ctx) },
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is BoxRaw -> Box(
            children = this.children.map { it.toDomain(ctx) },
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain()
        )

        is StatefulComponentRaw -> StatefulComponent(
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain(),
            states = this.states.map { it.toDomain(ctx) },
        )

        is DynamicColumnRaw -> DynamicColumn(
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain(),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplate = ctx.template(this.itemTemplateName),
        )

        is DynamicRowRaw -> DynamicRow(
            id = ComponentId(this.id),
            interactions = this.interactions.map(InteractionRaw::toDomain),
            margins = this.margins?.toDomain(),
            paddings = this.paddings?.toDomain(),
            width = this.width.toDomain(),
            height = this.height.toDomain(),
            backgroundColor = this.backgroundColor?.toDomain(ctx),
            border = this.border?.toDomain(ctx),
            shape = this.shape?.toDomain(),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplate = ctx.template(this.itemTemplateName),
        )
    }

private fun InsetsRaw.toDomain(): Insets =
    Insets(top = this.top, start = this.start, bottom = this.bottom, end = this.end)

private fun SizeRaw.toDomain(): Size =
    when (this) {
        is SizeRaw.WrapContentRaw -> Size.WrapContent()
        is SizeRaw.MatchParentRaw -> Size.MatchParent()
        is SizeRaw.FixedRaw -> Size.Fixed(this.value)
        is SizeRaw.WeightedRaw -> Size.Weighted(this.fraction)
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
            screenNavigationParams = this.screenNavigationParams.mapValues { ExpressionUtils.getValueOrExpression(it.value) }
        )

        is CommandActionRaw -> CommandAction(
            name = CommandName(this.name),
            params = this.params.mapValues { ExpressionUtils.getValueOrExpression(it.value) }
        )

        is NavigateToActionRaw -> NavigateToAction(
            screenName = ScreenName(this.screenName),
            screenNavigationParams = this.screenNavigationParams.mapValues { ExpressionUtils.getValueOrExpression(it.value) }
        )

        is NavigateBackActionRaw -> NavigateBackAction()
    }

private fun Any.toDomainValueOrExpression(): ValueOrExpression =
    ExpressionUtils.getValueOrExpression(this)

private fun ImageRaw.BadgeRaw.toDomain(ctx: MappingContext): Image.Badge =
    when (this) {
        is ImageRaw.BadgeRaw.BadgeWithImageRaw -> Image.Badge.BadgeWithImage(
            imageUrl = this.imageUrl
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
        text = ExpressionUtils.getValueOrExpression(this.text),
        textStyle = this.textStyle.toDomain(ctx),
        color = this.color.toDomain(ctx)
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
