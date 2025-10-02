package ru.hits.bdui.engine.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.externalApi.ApiRepresentationStorageService
import ru.hits.bdui.domain.api.ApiCallRepresentation
import ru.hits.bdui.domain.api.ApiRepresentationFromDatabase
import ru.hits.bdui.engine.Interpreter
import ru.hits.bdui.engine.api.caller.ExternalApiCaller
import ru.hits.bdui.engine.setVariables

data class ApiCallWithRepresentation(
    val apiCall: ApiCallRepresentation,
    val apiRepresentation: ApiRepresentationFromDatabase,
)

data class ApiCallWithRepresentationAndEndpointResults(
    val apiCall: ApiCallRepresentation,
    val apiRepresentation: ApiRepresentationFromDatabase,
    val endpointResults: Map<String, ExternalApiCaller.Response>,
)

sealed interface ExternalApisCallResult {
    data class Success(val data: Map<String, JsonNode>) : ExternalApisCallResult
    data class Error(val error: Throwable) : ExternalApisCallResult
}

//TODO(Провести рефакторинг реактивных цепочек, возможно компонента в целом)
@Component
class ExternalApiManager(
    private val apiCaller: ExternalApiCaller,
    private val objectMapper: ObjectMapper,
    private val expressionUtils: ExternalApiExpressionUtils,
    private val apiRepresentationStorageService: ApiRepresentationStorageService,
) {
    /**
     * @param interpreter интерпретатор для вычисления выражений, у которого !ВАЖНО! заданы все значения переменных, пришедших из навигации на экран
     * @param apiCalls список вызовов внешних API, которые необходимо выполнить
     */
    fun getData(
        interpreter: Interpreter,
        apiCalls: List<ApiCallRepresentation>
    ): Mono<ExternalApisCallResult> {
        return apiRepresentationStorageService
            .getApiRepresentations(apiCalls.map { it.apiId }.toSet())
            .zipWithApiCalls(apiCalls)
            .evaluateParameterExpressions(interpreter)
            .evaluateEndpointExpressions(interpreter)
            .callEndpoints()
            .filterApisWithFailedRequiredEndpoints()
            .executeMappingScripts(interpreter)
            .filter { (_, result) ->
                result != null
            }
            .flatMap { (alias, result) ->
                Mono.just(alias to objectMapper.readTree(result))
            }
            .collectList()
            .map<ExternalApisCallResult> { resultsList ->
                ExternalApisCallResult.Success(resultsList.associate { it.first to it.second })
            }
            .onErrorResume { throwable ->
                Mono.just(
                    ExternalApisCallResult.Error(throwable)
                )
            }
    }

    private fun Mono<List<ApiRepresentationFromDatabase>>.zipWithApiCalls(apiCalls: List<ApiCallRepresentation>) =
        this.flatMapMany { representations ->
            val apiCallsById = apiCalls.groupBy { it.apiId }
            Flux.fromIterable(representations)
                .flatMap { representation ->
                    val associatedApiCalls = apiCallsById[representation.id].orEmpty()
                    Flux.fromIterable(associatedApiCalls.map { apiCall ->
                        ApiCallWithRepresentation(apiCall, representation)
                    })
                }
        }

    private fun Flux<ApiCallWithRepresentation>.evaluateParameterExpressions(interpreter: Interpreter) =
        this.flatMap { callWithRepresentation ->
            Flux.fromIterable(callWithRepresentation.apiCall.apiParams.entries)
                .flatMap { (name, value) ->
                    Mono.just(name to expressionUtils.traverseJsonNodeAndReplaceExpressions(interpreter, value))
                }
                .collectMap({ it.first }, { it.second })
                .map { evaluatedParameters ->
                    callWithRepresentation.copy(
                        apiCall = callWithRepresentation.apiCall.copy(apiParams = evaluatedParameters),
                    )
                }
        }

    private fun Flux<ApiCallWithRepresentation>.evaluateEndpointExpressions(interpreter: Interpreter) =
        this.map { callWithRepresentation ->
            interpreter.scope { apiScopedInterpreter ->
                apiScopedInterpreter.setVariables(callWithRepresentation.apiCall.apiParams)

                val evaluatedEndpoints = callWithRepresentation.apiRepresentation.api.endpoints.map { endpoint ->
                    expressionUtils.evaluateEndpointExpressions(apiScopedInterpreter, endpoint)
                }
                callWithRepresentation.copy(
                    apiRepresentation = callWithRepresentation.apiRepresentation.copy(
                        api = callWithRepresentation.apiRepresentation.api.copy(
                            endpoints = evaluatedEndpoints,
                        )
                    ),
                )
            }
        }

    private fun Flux<ApiCallWithRepresentation>.callEndpoints() =
        this.flatMap { callWithRepresentation ->
            Flux.fromIterable(callWithRepresentation.apiRepresentation.api.endpoints)
                .flatMap { endpoint ->
                    apiCaller.call(endpoint)
                        .map { response -> endpoint.responseName to response }
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

    private fun Flux<ApiCallWithRepresentationAndEndpointResults>.filterApisWithFailedRequiredEndpoints() =
        this.filter { callWithRepresentationAndResults ->
            callWithRepresentationAndResults.apiRepresentation.api.endpoints.forEach { endpoint ->
                if (endpoint.isRequired && callWithRepresentationAndResults.endpointResults[endpoint.responseName] is ExternalApiCaller.Response.Error) {
                    return@filter false
                }
            }
            return@filter true
        }

    private fun Flux<ApiCallWithRepresentationAndEndpointResults>.executeMappingScripts(interpreter: Interpreter) =
        this.map { callWithRepresentationAndResults ->
            interpreter.scope { apiScopedInterpreter ->
                apiScopedInterpreter.setVariables(callWithRepresentationAndResults.apiCall.apiParams)
                val endpointResultVariables =
                    callWithRepresentationAndResults.endpointResults.filterValues { response ->
                        response is ExternalApiCaller.Response.Success
                    }.mapValues { (_, response) ->
                        (response as ExternalApiCaller.Response.Success).result
                    }
                apiScopedInterpreter.setVariables(endpointResultVariables)

                val mappingResult =
                    callWithRepresentationAndResults.apiRepresentation.api.mappingScript?.let { script ->
                        apiScopedInterpreter.execute(script)
                    }
                callWithRepresentationAndResults.apiCall.apiResultAlias to mappingResult
            }
        }
}
