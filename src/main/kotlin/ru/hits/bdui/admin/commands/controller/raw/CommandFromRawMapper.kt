package ru.hits.bdui.admin.commands.controller.raw

import ru.hits.bdui.admin.colorStyles.controller.raw.ColorStyleForSaveRaw
import ru.hits.bdui.admin.commands.models.UpdateCommand
import ru.hits.bdui.domain.CommandName
import ru.hits.bdui.domain.api.ApiCallRepresentation
import ru.hits.bdui.domain.command.Command
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.color.ColorStyleFromDatabase
import ru.hits.bdui.domain.template.ComponentTemplateFromDatabase
import ru.hits.bdui.engine.expression.ExpressionUtils
import java.util.UUID

object CommandFromRawMapper {
    fun UpdateCommand(id: UUID, raw: CommandForSaveRaw, template: ComponentTemplateFromDatabase?): UpdateCommand =
        UpdateCommand(
            id = id,
            command = Command(raw, template),
        )

    fun Command(raw: CommandForSaveRaw, template: ComponentTemplateFromDatabase?): Command =
        Command(
            name = CommandName(raw.name),
            commandParams = raw.params,
            apis = raw.apis.associate {
                it.alias to ApiCallRepresentation(
                    apiResultAlias = it.alias,
                    apiId = it.id,
                    apiParams = it.params.associate { entry ->
                        entry.name to entry.value
                    }
                )
            },
            itemTemplate = template,
            fallbackMessage = raw.fallbackMessage,
        )

    fun ColorStyleFromDatabase(id: UUID, raw: ColorStyleForSaveRaw): ColorStyleFromDatabase =
        ColorStyleFromDatabase(
            id = id,
            colorStyle = ColorStyle(
                token = ExpressionUtils.getValueOrExpression(raw.token),
                color = ExpressionUtils.getValueOrExpression(raw.color),
            )
        )
}