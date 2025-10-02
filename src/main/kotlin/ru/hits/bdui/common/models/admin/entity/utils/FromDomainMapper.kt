package ru.hits.bdui.common.models.admin.entity.utils

import ru.hits.bdui.common.models.admin.entity.command.CommandEntityJson
import ru.hits.bdui.common.models.admin.entity.components.BoxEntity
import ru.hits.bdui.common.models.admin.entity.components.ButtonEntity
import ru.hits.bdui.common.models.admin.entity.components.ColumnEntity
import ru.hits.bdui.common.models.admin.entity.components.ComponentBaseEntityProperties
import ru.hits.bdui.common.models.admin.entity.components.ComponentEntity
import ru.hits.bdui.common.models.admin.entity.components.ComponentTemplateEntity
import ru.hits.bdui.common.models.admin.entity.components.ComponentTemplateFromDatabaseEntity
import ru.hits.bdui.common.models.admin.entity.components.DynamicColumnEntity
import ru.hits.bdui.common.models.admin.entity.components.DynamicRowEntity
import ru.hits.bdui.common.models.admin.entity.components.ImageEntity
import ru.hits.bdui.common.models.admin.entity.components.InputEntity
import ru.hits.bdui.common.models.admin.entity.components.MaskEntity
import ru.hits.bdui.common.models.admin.entity.components.ProgressBarEntity
import ru.hits.bdui.common.models.admin.entity.components.RowEntity
import ru.hits.bdui.common.models.admin.entity.components.SpacerEntity
import ru.hits.bdui.common.models.admin.entity.components.StateDefinitionEntity
import ru.hits.bdui.common.models.admin.entity.components.StatefulComponentEntity
import ru.hits.bdui.common.models.admin.entity.components.SwitchEntity
import ru.hits.bdui.common.models.admin.entity.components.TextEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.BorderEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.RegexEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.ShapeEntity
import ru.hits.bdui.common.models.admin.entity.components.additional.ShapeTypeEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.InsetsEntity
import ru.hits.bdui.common.models.admin.entity.components.properties.SizeEntity
import ru.hits.bdui.common.models.admin.entity.interactions.InteractionEntity
import ru.hits.bdui.common.models.admin.entity.interactions.InteractionTypeEntity
import ru.hits.bdui.common.models.admin.entity.interactions.actions.ActionEntity
import ru.hits.bdui.common.models.admin.entity.interactions.actions.CommandActionEntity
import ru.hits.bdui.common.models.admin.entity.interactions.actions.NavigateBackActionEntity
import ru.hits.bdui.common.models.admin.entity.interactions.actions.NavigateToActionEntity
import ru.hits.bdui.common.models.admin.entity.interactions.actions.UpdateScreenActionEntity
import ru.hits.bdui.common.models.admin.entity.screen.ApiCallRepresentationEntity
import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity
import ru.hits.bdui.common.models.admin.entity.styles.text.TextDecorationEntity
import ru.hits.bdui.common.models.admin.entity.styles.text.TextStyleEntity
import ru.hits.bdui.common.models.admin.entity.styles.text.TextWithStyleEntity
import ru.hits.bdui.domain.command.Command
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
import ru.hits.bdui.domain.template.ComponentTemplate
import ru.hits.bdui.domain.template.ComponentTemplateFromDatabase

fun Component.toEntity(): ComponentEntity =
    when (this) {
        is Text -> TextEntity(
            textWithStyle = this.textWithStyle.toEntity(),
            base = this.base.toEntity(),
        )

        is Input -> InputEntity(
            textWithStyle = this.textWithStyle.toEntity(),
            mask = this.mask?.let {
                when (it) {
                    Mask.PHONE -> MaskEntity.PHONE
                }
            },
            regex = this.regex?.let {
                when (it) {
                    Regex.EMAIL -> RegexEntity.EMAIL
                }
            },
            rightIcon = this.rightIcon?.toEntity() as ImageEntity?,
            hint = this.hint?.let {
                InputEntity.HintEntity(
                    it.textWithStyle.toEntity()
                )
            },
            placeholder = this.placeholder?.let {
                InputEntity.PlaceholderEntity(
                    it.textWithStyle.toEntity()
                )
            },
            base = this.base.toEntity(),
        )

        is Image -> ImageEntity(
            imageUrl = this.imageUrl.value as String,
            badge = this.badge?.toEntity(),
            base = this.base.toEntity(),
        )

        is Spacer -> SpacerEntity(
            base = this.base.toEntity(),
        )

        is ProgressBar -> ProgressBarEntity(
            base = this.base.toEntity(),
        )

        is Switch -> SwitchEntity(
            base = this.base.toEntity(),
        )

        is Button -> ButtonEntity(
            textWithStyle = this.textWithStyle.toEntity(),
            enabled = this.enabled,
            base = this.base.toEntity(),
        )

        is Column -> ColumnEntity(
            children = this.children.map { it.toEntity() },
            base = this.base.toEntity(),
        )

        is Row -> RowEntity(
            children = this.children.map { it.toEntity() },
            base = this.base.toEntity(),
        )

        is Box -> BoxEntity(
            children = this.children.map { it.toEntity() },
            base = this.base.toEntity(),
        )

        is StatefulComponent -> StatefulComponentEntity(
            base = this.base.toEntity(),
            states = this.states.map { it.toEntity() },
        )

        is DynamicColumn -> DynamicColumnEntity(
            base = this.base.toEntity(),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplate = this.itemTemplate.toEntity()
        )

        is DynamicRow -> DynamicRowEntity(
            base = this.base.toEntity(),
            itemsData = this.itemsData,
            itemAlias = this.itemAlias,
            itemTemplate = this.itemTemplate.toEntity()
        )
    }

fun Command.toEntity(): CommandEntityJson =
    CommandEntityJson(
        name = this.name.value,
        commandParams = this.commandParams,
        apis = this.apis.mapValues { entry ->
            ApiCallRepresentationEntity(
                apiResultAlias = entry.value.apiResultAlias,
                apiId = entry.value.apiId,
                apiParams = entry.value.apiParams,
            )
        },
        itemTemplate = this.itemTemplate?.toEntity(),
        fallbackMessage = this.fallbackMessage,
    )

private fun ComponentTemplateFromDatabase.toEntity(): ComponentTemplateFromDatabaseEntity =
    ComponentTemplateFromDatabaseEntity(
        id = this.id,
        createdAtTimestampMs = this.createdAtTimestampMs,
        lastModifiedTimestampMs = this.lastModifiedTimestampMs,
        template = this.template.toEntity()
    )

private fun ComponentTemplate.toEntity(): ComponentTemplateEntity =
    ComponentTemplateEntity(
        name = this.name.value,
        component = this.component.toEntity()
    )

private fun ComponentBaseProperties.toEntity(): ComponentBaseEntityProperties =
    ComponentBaseEntityProperties(
        id = this.id.value.value as String,
        interactions = this.interactions.map(Interaction::toEntity),
        margins = this.margins?.toEntity(),
        paddings = this.paddings?.toEntity(),
        width = this.width.toEntity(),
        height = this.height.toEntity(),
        backgroundColor = this.backgroundColor?.toEntity(),
        border = this.border?.toEntity(),
        shape = this.shape?.toEntity(),
    )

private fun Insets.toEntity(): InsetsEntity =
    InsetsEntity(top = this.top, start = this.start, bottom = this.bottom, end = this.end)

private fun Size.toEntity(): SizeEntity =
    when (this) {
        is Size.WrapContent -> SizeEntity.WrapContentEntity()
        is Size.MatchParent -> SizeEntity.MatchParentEntity()
        is Size.Fixed -> SizeEntity.FixedEntity(this.value)
        is Size.Weighted -> SizeEntity.WeightedEntity(this.fraction)
    }

private fun Border.toEntity(): BorderEntity =
    BorderEntity(
        thickness = this.thickness,
        color = this.color.toEntity()
    )

private fun Shape.toEntity(): ShapeEntity =
    ShapeEntity(
        type = when (this.type) {
            ShapeType.ROUNDED_CORNERS -> ShapeTypeEntity.ROUNDED_CORNERS
        },
        topRight = this.topRight,
        topLeft = this.topLeft,
        bottomRight = this.bottomRight,
        bottomLeft = this.bottomLeft,
    )

private fun Interaction.toEntity(): InteractionEntity =
    InteractionEntity(
        type = when (this.type) {
            InteractionType.ON_CLICK -> InteractionTypeEntity.ON_CLICK
            InteractionType.ON_SHOW -> InteractionTypeEntity.ON_SHOW
        },
        actions = this.actions.map(Action::toEntity)
    )

private fun Action.toEntity(): ActionEntity =
    when (this) {
        is UpdateScreenAction -> UpdateScreenActionEntity(
            screenName = this.screenName.value,
            screenNavigationParams = this.screenNavigationParams.mapValues { it.value.value as String }
        )

        is CommandAction -> CommandActionEntity(
            name = this.name.value,
            params = this.params.mapValues { it.value.value as String }
        )

        is NavigateToAction -> NavigateToActionEntity(
            screenName = this.screenName.value,
            screenNavigationParams = this.screenNavigationParams.mapValues { it.value.value as String }
        )

        is NavigateBackAction -> NavigateBackActionEntity()
    }

private fun Image.Badge.toEntity(): ImageEntity.BadgeEntity =
    when (this) {
        is Image.Badge.BadgeWithImage -> ImageEntity.BadgeEntity.BadgeWithImageEntity(
            imageUrl = this.imageUrl.value as String
        )

        is Image.Badge.BadgeWithText -> ImageEntity.BadgeEntity.BadgeWithTextEntity(
            textWithStyle = textWithStyle.toEntity()
        )
    }

private fun StateDefinition.toEntity(): StateDefinitionEntity =
    StateDefinitionEntity(
        condition = this.condition.value,
        component = this.component.toEntity()
    )

private fun TextWithStyle.toEntity(): TextWithStyleEntity =
    TextWithStyleEntity(
        text = this.text.value as String,
        textStyle = this.textStyle.toEntity(),
        colorStyle = this.color.toEntity()
    )

private fun TextStyle.toEntity(): TextStyleEntity =
    TextStyleEntity(
        token = this.token,
        decoration = this.decoration?.let {
            when (it) {
                TextDecoration.REGULAR -> TextDecorationEntity.REGULAR
                TextDecoration.ITALIC -> TextDecorationEntity.ITALIC
                TextDecoration.BOLD -> TextDecorationEntity.BOLD
                TextDecoration.UNDERLINE -> TextDecorationEntity.UNDERLINE
                TextDecoration.STRIKETHROUGH_RED -> TextDecorationEntity.STRIKETHROUGH_RED
                TextDecoration.STRIKETHROUGH -> TextDecorationEntity.STRIKETHROUGH
                TextDecoration.OVERLINE -> TextDecorationEntity.OVERLINE
            }
        },
        weight = this.weight,
        size = this.size,
        lineHeight = this.lineHeight,
    )


private fun ColorStyle.toEntity(): ColorStyleEntity =
    ColorStyleEntity(
        token = this.token.value as String,
        color = this.color.value as String,
    )
