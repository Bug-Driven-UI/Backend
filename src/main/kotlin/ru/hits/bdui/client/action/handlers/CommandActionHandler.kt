package ru.hits.bdui.client.action.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.commands.CommandService
import ru.hits.bdui.client.action.ActionHandler
import ru.hits.bdui.client.action.controller.raw.actions.request.ActionRawRequest
import ru.hits.bdui.client.action.controller.raw.actions.request.CommandActionRawRequest
import ru.hits.bdui.client.action.controller.raw.actions.response.ActionResponseRaw
import ru.hits.bdui.client.action.controller.raw.actions.response.CommandActionResponseRaw
import ru.hits.bdui.client.action.controller.raw.actions.response.CommandResponseData
import ru.hits.bdui.client.action.controller.raw.actions.response.CommandResponsePayload
import ru.hits.bdui.common.exceptions.BadRequestException
import ru.hits.bdui.common.models.client.raw.utils.toRendered
import ru.hits.bdui.domain.CommandName
import ru.hits.bdui.engine.api.ExternalApiManager
import ru.hits.bdui.engine.api.ExternalApisCallResult
import ru.hits.bdui.engine.expression.JSInterpreter
import ru.hits.bdui.engine.screen.ComponentEvaluationGateway
import ru.hits.bdui.engine.setVariables

@Component
class CommandActionHandler(
    private val commandService: CommandService,
    private val objectMapper: ObjectMapper,
    private val apiManager: ExternalApiManager,
    private val gateway: ComponentEvaluationGateway
) : ActionHandler {
    override val type: String = "command"

    override fun execute(action: ActionRawRequest): Mono<ActionResponseRaw> {
        require(action is CommandActionRawRequest)

        val interpreter = JSInterpreter(objectMapper)
        interpreter.setVariables(action.params)

        return commandService.findByName(CommandName(action.name))
            .map { commandFromDatabase ->
                val requiredParams = commandFromDatabase.command.commandParams

                val missingParams = requiredParams.filter { it !in action.params.keys }
                if (missingParams.isNotEmpty()) {
                    // TODO(Подумать стоит ли выкидывать ошибку)
                    throw BadRequestException("Отсутствуют обязательные параметры: ${missingParams.joinToString(", ")}")
                }

                commandFromDatabase
            }
            .flatMap { commandFromDatabase ->
                val apiCalls = commandFromDatabase.command.apis.values.toList()
                apiManager.getData(interpreter, apiCalls)
                    .map { apiData ->
                        when (apiData) {
                            is ExternalApisCallResult.Success -> interpreter.setVariables(apiData.data)
                            is ExternalApisCallResult.Error -> throw apiData.error
                        }
                    }
                    .then(commandFromDatabase.toMono())
            }
            .map {
                CommandActionResponseRaw(
                    name = action.name,
                    response = CommandResponsePayload(
                        data = CommandResponseData(
                            component = it.command.itemTemplate?.template?.component?.let { component ->
                                gateway.evaluate(
                                    component,
                                    interpreter
                                ).toRendered()
                            },
                            fallbackMessage = it.command.fallbackMessage,
                        )
                    )
                )
            }
    }
}