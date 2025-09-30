package ru.hits.bdui.common.models.admin.entity.utils

import ru.hits.bdui.common.models.admin.entity.components.*
import ru.hits.bdui.common.models.admin.entity.components.additional.BorderEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.ShapeEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.ShapeTypeEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.InsetsEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.SizeEntity
import ru.hits.bdui.common.models.admin.entity.interactions.InteractionEntity
import ru.hits.bdui.common.models.admin.entity.interactions.InteractionTypeEntity
import ru.hits.bdui.common.models.admin.entity.interactions.actions.*
import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity
import ru.hits.bdui.common.models.admin.entity.styles.text.TextDecorationEntity
import ru.hits.bdui.common.models.admin.entity.styles.text.TextStyleEntity
import ru.hits.bdui.common.models.admin.entity.styles.text.TextWithStyleEntity
import ru.hits.bdui.domain.*
import ru.hits.bdui.domain.screen.components.*
import ru.hits.bdui.domain.screen.components.additional.Border
import ru.hits.bdui.domain.screen.components.additional.Regex
import ru.hits.bdui.domain.screen.components.additional.Shape
import ru.hits.bdui.domain.screen.components.additional.ShapeType
import ru.hits.bdui.domain.screen.components.properties.Insets
import ru.hits.bdui.domain.screen.components.properties.Size
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.interactions.InteractionType
import ru.hits.bdui.domain.screen.interactions.actions.*
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.text.TextDecoration
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.screen.styles.text.TextWithStyle
import ru.hits.bdui.domain.template.ComponentTemplate
import ru.hits.bdui.engine.expression.ExpressionUtils
import ru.hits.bdui.engine.expression.ExpressionUtils.getValueOrExpression

fun ComponentEntity.toDomain(): Component =
    when (this) {
        is TextEntity -> Text(
            textWithStyle = this.textWithStyle.toDomain(),
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain()
            ),
        )

        is InputEntity -> Input(
            textWithStyle = this.textWithStyle.toDomain(),
            mask = this.mask?.let { Mask.valueOf(it.name) },
            regex = this.regex?.let { Regex.valueOf(it.name) },
            rightIcon = this.rightIcon?.toDomain() as Image?,
            hint = this.hint?.let { Input.Hint(it.textWithStyle.toDomain()) },
            placeholder = this.placeholder?.let { Input.Placeholder(it.textWithStyle.toDomain()) },
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            )
        )

        is ImageEntity -> Image(
            imageUrl = this.imageUrl.toDomainValueOrExpression(),
            badge = this.badge?.toDomain(),
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            )
        )

        is SpacerEntity -> Spacer(
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            )
        )

        is ProgressBarEntity -> ProgressBar(
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            )
        )

        is SwitchEntity -> Switch(
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            )
        )

        is ButtonEntity -> Button(
            textWithStyle = this.textWithStyle.toDomain(),
            enabled = this.enabled,
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            )
        )

        is ColumnEntity -> Column(
            children = this.children.map { it.toDomain() },
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            )
        )

        is RowEntity -> Row(
            children = this.children.map { it.toDomain() },
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            )
        )

        is BoxEntity -> Box(
            children = this.children.map { it.toDomain() },
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            )
        )

        is StatefulComponentEntity -> StatefulComponent(
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            ),
            states = this.states.map { it.toDomain() },
        )

        is DynamicColumnEntity -> DynamicColumn(
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            ),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplate = this.itemTemplate.toDomain()
        )

        is DynamicRowEntity -> DynamicRow(
            basePropertiesSet = ComponentPropertiesSet(
                id = ComponentId(getValueOrExpression(this.id)),
                interactions = this.interactions.map(InteractionEntity::toDomain),
                margins = this.margins?.toDomain(),
                paddings = this.paddings?.toDomain(),
                width = this.width.toDomain(),
                height = this.height.toDomain(),
                backgroundColor = this.backgroundColor?.toDomain(),
                border = this.border?.toDomain(),
                shape = this.shape?.toDomain(),
            ),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplate = this.itemTemplate.toDomain(),
        )
    }

private fun ComponentTemplateEntity.toDomain(): ComponentTemplate =
    ComponentTemplate(
        name = TemplateName(this.name),
        component = this.component.toDomain()
    )

private fun InsetsEntity.toDomain(): Insets =
    Insets(top = this.top, start = this.start, bottom = this.bottom, end = this.end)

private fun SizeEntity.toDomain(): Size =
    when (this) {
        is SizeEntity.WrapContentEntity -> Size.WrapContent()
        is SizeEntity.MatchParentEntity -> Size.MatchParent()
        is SizeEntity.FixedEntity -> Size.Fixed(this.value)
        is SizeEntity.WeightedEntity -> Size.Weighted(this.fraction)
    }

private fun BorderEntity.toDomain(): Border =
    Border(
        thickness = this.thickness,
        color = this.color.toDomain()
    )

private fun ShapeEntity.toDomain(): Shape =
    Shape(
        type = when (this.type) {
            ShapeTypeEntity.ROUNDED_CORNERS -> ShapeType.ROUNDED_CORNERS
        },
        topRight = this.topRight,
        topLeft = this.topLeft,
        bottomRight = this.bottomRight,
        bottomLeft = this.bottomLeft,
    )

private fun InteractionEntity.toDomain(): Interaction =
    Interaction(
        type = when (this.type) {
            InteractionTypeEntity.ON_CLICK -> InteractionType.ON_CLICK
            InteractionTypeEntity.ON_SHOW -> InteractionType.ON_SHOW
        },
        actions = this.actions.map(ActionEntity::toDomain)
    )

private fun ActionEntity.toDomain(): Action =
    when (this) {
        is UpdateScreenActionEntity -> UpdateScreenAction(
            screenName = ScreenName(this.screenName),
            screenNavigationParams = this.screenNavigationParams.mapValues { getValueOrExpression(it.value) }
        )

        is CommandActionEntity -> CommandAction(
            name = CommandName(this.name),
            params = this.params.mapValues { getValueOrExpression(it.value) }
        )

        is NavigateToActionEntity -> NavigateToAction(
            screenName = ScreenName(this.screenName),
            screenNavigationParams = this.screenNavigationParams.mapValues { getValueOrExpression(it.value) }
        )

        is NavigateBackActionEntity -> NavigateBackAction()
    }

private fun Any.toDomainValueOrExpression(): ValueOrExpression =
    getValueOrExpression(this)

private fun ImageEntity.BadgeEntity.toDomain(): Image.Badge =
    when (this) {
        is ImageEntity.BadgeEntity.BadgeWithImageEntity -> Image.Badge.BadgeWithImage(
            imageUrl = getValueOrExpression(this.imageUrl)
        )

        is ImageEntity.BadgeEntity.BadgeWithTextEntity -> Image.Badge.BadgeWithText(
            textWithStyle = textWithStyle.toDomain()
        )
    }

private fun StateDefinitionEntity.toDomain(): StateDefinition =
    StateDefinition(
        condition = ExpressionUtils.getExpressionOrThrow(this.condition),
        component = this.component.toDomain()
    )

private fun TextWithStyleEntity.toDomain(): TextWithStyle =
    TextWithStyle(
        text = getValueOrExpression(this.text),
        textStyle = this.textStyle.toDomain(),
        color = this.color.toDomain()
    )

private fun TextStyleEntity.toDomain(): TextStyle =
    TextStyle(
        token = this.token,
        decoration = this.decoration?.let {
            when (it) {
                TextDecorationEntity.REGULAR -> TextDecoration.REGULAR
                TextDecorationEntity.ITALIC -> TextDecoration.ITALIC
                TextDecorationEntity.BOLD -> TextDecoration.BOLD
                TextDecorationEntity.UNDERLINE -> TextDecoration.UNDERLINE
                TextDecorationEntity.STRIKETHROUGH_RED -> TextDecoration.STRIKETHROUGH_RED
                TextDecorationEntity.STRIKETHROUGH -> TextDecoration.STRIKETHROUGH
                TextDecorationEntity.OVERLINE -> TextDecoration.OVERLINE
            }
        },
        weight = this.weight,
        size = this.size,
        lineHeight = this.lineHeight,
    )


private fun ColorStyleEntity.toDomain(): ColorStyle =
    ColorStyle(
        token = getValueOrExpression(this.token),
        color = getValueOrExpression(this.color),
    )
