package ru.hits.bdui.admin.screen

import com.fasterxml.jackson.databind.ObjectMapper
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.screen.controller.raw.ApiCallRepresentationRaw
import ru.hits.bdui.admin.screen.controller.raw.ScreenRaw
import ru.hits.bdui.common.components.BatchValidationOutcome
import ru.hits.bdui.common.components.ComponentValidationService
import ru.hits.bdui.common.models.api.ErrorContentRaw
import ru.hits.bdui.domain.ScreenName
import ru.hits.bdui.domain.api.ApiCallRepresentation
import ru.hits.bdui.domain.screen.Scaffold
import ru.hits.bdui.domain.screen.Screen
import ru.hits.bdui.domain.screen.components.Component

sealed interface ScreenValidationOutcome {
    data class Success(val screen: Screen) : ScreenValidationOutcome
    data class Error(val errors: List<ErrorContentRaw>) : ScreenValidationOutcome
}

@org.springframework.stereotype.Component
class ScreenValidationService(
    private val objectMapper: ObjectMapper,
    private val componentValidationService: ComponentValidationService,
) {
    //TODO(Подумать как оптимизировать валидацию экрана, чтоб не собирать отдельно компоненты скаффолда и экрана)
    fun validateAndMap(screenRaw: ScreenRaw): Mono<ScreenValidationOutcome> {
        val allRaws = buildList {
            addAll(screenRaw.components)
            screenRaw.scaffold?.topBar?.let { add(it) }
            screenRaw.scaffold?.bottomBar?.let { add(it) }
        }


        if (allRaws.isEmpty()) {
            return runCatching {
                val screen = Screen(screenRaw, emptyList(), null, null)
                ScreenValidationOutcome.Success(screen)
            }.getOrElse { e ->
                ScreenValidationOutcome.Error(
                    listOf(ErrorContentRaw.emerge("Ошибка маппинга пустого экрана: ${e.message}"))
                )
            }.toMono()
        }

        return componentValidationService.validateAndMapAll(allRaws)
            .map { outcome ->
                when (outcome) {
                    is BatchValidationOutcome.Error ->
                        ScreenValidationOutcome.Error(outcome.errors)

                    is BatchValidationOutcome.Success -> {
                        // Такая логика работает, пока компоненты маппятся последовательно
                        val mapped = outcome.components
                        val baseCount = screenRaw.components.size
                        var idx = baseCount

                        val baseComponents = mapped.take(baseCount)
                        val topBar =
                            if (screenRaw.scaffold?.topBar != null) mapped[idx++] else null
                        val bottomBar =
                            if (screenRaw.scaffold?.bottomBar != null) mapped.getOrNull(idx) else null

                        val screen = Screen(screenRaw, baseComponents, topBar, bottomBar)
                        ScreenValidationOutcome.Success(screen)
                    }
                }
            }
            .onErrorResume { e ->
                ScreenValidationOutcome.Error(
                    listOf(ErrorContentRaw.emerge("Ошибка валидации экрана: ${e.message}"))
                ).toMono()
            }
    }

    private fun Screen(
        raw: ScreenRaw,
        baseComponents: List<Component>,
        topBar: Component?,
        bottomBar: Component?,
    ): Screen =
        Screen(
            screenName = ScreenName(raw.screenName),
            description = raw.description,
            screenNavigationParams = raw.screenNavigationParams,
            apis = raw.apis.map(::ApiCallRepresentation),
            components = baseComponents,
            scaffold = Scaffold(
                topBar = topBar,
                bottomBar = bottomBar
            )
        )

    private fun ApiCallRepresentation(raw: ApiCallRepresentationRaw): ApiCallRepresentation =
        ApiCallRepresentation(
            apiResultAlias = raw.alias,
            apiId = raw.id,
            apiParams = raw.params.associate { param ->
                param.name to objectMapper.valueToTree(param.value)
            }
        )
}