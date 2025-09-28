package ru.hits.bdui.core.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hits.bdui.core.api.caller.ExternalApiCaller
import ru.hits.bdui.core.api.model.ExternalApiCallModel
import ru.hits.bdui.core.expression.Interpreter
import ru.hits.bdui.model.api.ApiRepresentationModel
import ru.hits.bdui.model.api.ApiRepresentationsGetModel
import ru.hits.bdui.model.common.ErrorModel
import ru.hits.bdui.service.ApiRepresentationStorageService

// TODO протестировать и отрефакторить
data class ApiCallWithRepresentation(
    val apiCall: ExternalApiCallModel,
    val apiRepresentation: ApiRepresentationModel,
)

data class ApiCallWithRepresentationAndEndpointResults(
    val apiCall: ExternalApiCallModel,
    val apiRepresentation: ApiRepresentationModel,
    val endpointResults: Map<String, ExternalApiCaller.Response>,
)

sealed interface ExternalApisCallResult {
    data class Success(val data: Map<String, JsonNode>) : ExternalApisCallResult
    data class Error(val error: ErrorModel) : ExternalApisCallResult
}

@Component
class ExternalApiManager(
    private val apiCaller: ExternalApiCaller,
    private val objectMapper: ObjectMapper,
    private val expressionUtils: ExternalApiExpressionUtils,
    private val apiRepresentationStorageService: ApiRepresentationStorageService,
) {

    fun getData(
        interpreter: Interpreter,
        inputVariables: Map<String, JsonNode>,
        apiCalls: List<ExternalApiCallModel>
    ): Mono<ExternalApisCallResult> = interpreter.scope { scopedInterpreter ->
        inputVariables.forEach { (name, value) ->
            scopedInterpreter.setVariable(name, value)
        }

        val apisGetModel = ApiRepresentationsGetModel(
            ids = apiCalls.map { it.apiRepresentationId },
        )

        val apiRepresentations = apiRepresentationStorageService.getApiRepresentations(apisGetModel)
        val apiCallToRepresentation = apiCalls.mapNotNull { apiCall ->
            val representation = apiRepresentations.find { representation ->
                representation.id == apiCall.apiRepresentationId
            }
            if (representation == null) {
                null
            } else {
                ApiCallWithRepresentation(apiCall, representation)
            }
        }

        Flux.fromIterable(apiCallToRepresentation)
            .flatMap { callWithRepresentation ->
                Flux.fromIterable(callWithRepresentation.apiCall.parameterValues.entries)
                    .flatMap { (name, value) ->
                        Mono.just(name to expressionUtils.traverseJsonNodeAndReplaceExpressions(scopedInterpreter, value))
                    }
                    .collectMap({ it.first }, { it.second })
                    .map { evaluatedParameters ->
                        callWithRepresentation.copy(
                            apiCall = callWithRepresentation.apiCall.copy(parameterValues = evaluatedParameters),
                        )
                    }
            }
            .map { callWithRepresentation ->
                scopedInterpreter.scope { apiScopedInterpreter ->
                    callWithRepresentation.apiCall.parameterValues.forEach { (name, value) ->
                        apiScopedInterpreter.setVariable(name, value)
                    }
                    val evaluatedEndpoints = callWithRepresentation.apiRepresentation.endpoints.map { endpoint ->
                        expressionUtils.evaluateEndpointExpressions(apiScopedInterpreter, endpoint)
                    }
                    callWithRepresentation.copy(
                        apiRepresentation = callWithRepresentation.apiRepresentation.copy(
                            endpoints = evaluatedEndpoints,
                        ),
                    )
                }
            }
            .flatMap { callWithRepresentation ->
                Flux.fromIterable(callWithRepresentation.apiRepresentation.endpoints)
                    .flatMap { endpoint ->
                        apiCaller.call(endpoint).map { response -> endpoint.responseName to response  }
                    }
                    .collectMap({ it.first }, { it.second })
                    .map { endpointResults ->
                        ApiCallWithRepresentationAndEndpointResults(
                            callWithRepresentation.apiCall,
                            callWithRepresentation.apiRepresentation,
                            endpointResults,
                        )
                    }
            }
            .filter { callWithRepresentationAndResults ->
                callWithRepresentationAndResults.apiRepresentation.endpoints.forEach { endpoint ->
                    if (endpoint.isRequired && callWithRepresentationAndResults.endpointResults[endpoint.responseName] is ExternalApiCaller.Response.Error) {
                        return@filter false
                    }
                }
                return@filter true
            }
            .map { callWithRepresentationAndResults ->
                scopedInterpreter.scope { apiScopedInterpreter ->
                    callWithRepresentationAndResults.apiCall.parameterValues.forEach { (name, value) ->
                        apiScopedInterpreter.setVariable(name, value)
                    }
                    callWithRepresentationAndResults.endpointResults.forEach { (responseName, response) ->
                        if (response is ExternalApiCaller.Response.Success) {
                            apiScopedInterpreter.setVariable(responseName, response.result)
                        }
                    }

                    val apiResult = apiScopedInterpreter.execute(callWithRepresentationAndResults.apiRepresentation.mappingScript)
                    callWithRepresentationAndResults.apiCall.resultAlias to apiResult
                }
            }
            .filter { (alias, result) ->
                result != null
            }
            .flatMap { (alias, result) ->
                Mono.just(alias to objectMapper.readTree(result))
            }
            .collectList()
            .map<ExternalApisCallResult> { resultsList ->
                ExternalApisCallResult.Success(resultsList.associate { it.first to it.second })
            }
            .onErrorResume {
                Mono.just(
                    ExternalApisCallResult.Error(
                        ErrorModel(
                            message = "Не удалось получить данные от внешних API",
                            timestampMs = System.currentTimeMillis(),
                        )
                    )
                )
            }
    }
}
