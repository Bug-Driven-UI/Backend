package ru.hits.bdui.common.components

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.colorStyles.database.ColorStyleRepository
import ru.hits.bdui.admin.templates.database.TemplateRepository
import ru.hits.bdui.admin.textStyles.database.TextStyleRepository
import ru.hits.bdui.common.components.validation.MappingContext
import ru.hits.bdui.common.components.validation.NeededRefsCollector
import ru.hits.bdui.common.models.admin.raw.components.ComponentRaw
import ru.hits.bdui.common.models.admin.raw.utils.toDomain
import ru.hits.bdui.common.models.api.ErrorContentRaw
import ru.hits.bdui.domain.screen.components.Component
import ru.hits.bdui.domain.screen.styles.color.ColorStyle
import ru.hits.bdui.domain.screen.styles.text.TextStyle
import ru.hits.bdui.domain.template.ComponentTemplate

sealed interface ValidationOutcome {
    data class Success(val component: Component) : ValidationOutcome
    data class Error(val error: List<ErrorContentRaw>) : ValidationOutcome
}

sealed interface BatchValidationOutcome {
    data class Success(val components: List<Component>) : BatchValidationOutcome
    data class Error(val errors: List<ErrorContentRaw>) : BatchValidationOutcome
}

@Service
class ComponentValidationService(
    private val refsCollector: NeededRefsCollector,
    private val colorStyleRepository: ColorStyleRepository,
    private val textStyleRepository: TextStyleRepository,
    private val templateRepository: TemplateRepository,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun validateAndMapAll(raws: List<ComponentRaw>): Mono<BatchValidationOutcome> {
        if (raws.isEmpty()) return BatchValidationOutcome.Success(emptyList()).toMono()

        val neededDataRefs = refsCollector.collect(raws)

        val colorsMono: Mono<Map<String, ColorStyle>> =
            if (neededDataRefs.colorTokens.isEmpty()) {
                emptyMap<String, ColorStyle>().toMono()
            } else {
                colorStyleRepository.findAllByTokens(neededDataRefs.colorTokens)
                    .map { response ->
                        when (response) {
                            is ColorStyleRepository.FindAllResponse.Success ->
                                response.colorStyles.associateBy({ it.colorStyle.token.value as String }) {
                                    ColorStyle(
                                        token = it.colorStyle.token,
                                        color = it.colorStyle.color
                                    )
                                }

                            is ColorStyleRepository.FindAllResponse.Error -> throw response.error
                        }
                    }
            }

        val textsMono: Mono<Map<String, TextStyle>> =
            if (neededDataRefs.textTokens.isEmpty()) {
                emptyMap<String, TextStyle>().toMono()
            } else {
                textStyleRepository.findAllByTokens(neededDataRefs.textTokens)
                    .map { response ->
                        when (response) {
                            is TextStyleRepository.FindAllResponse.Success ->
                                response.textStyles.associateBy({ it.textStyle.token }) { it.textStyle }

                            is TextStyleRepository.FindAllResponse.Error -> throw response.error
                        }
                    }
            }

        val templatesMono: Mono<Map<String, ComponentTemplate>> =
            if (neededDataRefs.templateNames.isEmpty()) {
                emptyMap<String, ComponentTemplate>().toMono()
            } else {
                templateRepository.findAllByNameIn(neededDataRefs.templateNames)
                    .map { response ->
                        when (response) {
                            is TemplateRepository.FindAllResponse.Success ->
                                response.templates.associateBy({ it.template.name.value }) { it.template }

                            is TemplateRepository.FindAllResponse.Error -> throw response.error
                        }
                    }
            }

        return Mono.zip(colorsMono, textsMono, templatesMono)
            .map { tuple ->
                val colors = tuple.t1
                val texts = tuple.t2
                val templates = tuple.t3

                val errors = mutableListOf<ErrorContentRaw>()

                neededDataRefs.duplicatedIds.forEach { id ->
                    errors += ErrorContentRaw.emerge("В компоненте содержится повторяющийся id='$id'")
                }

                (neededDataRefs.colorTokens - colors.keys).forEach {
                    errors += ErrorContentRaw.emerge("Не найден цвет по токену='$it'")
                }
                (neededDataRefs.textTokens - texts.keys).forEach {
                    errors += ErrorContentRaw.emerge("Не найден стиль текста по токену='$it'")
                }
                (neededDataRefs.templateNames - templates.keys).forEach {
                    errors += ErrorContentRaw.emerge("Не найден шаблон компонента по имени='$it'")
                }

                if (errors.isNotEmpty()) {
                    return@map BatchValidationOutcome.Error(errors)
                }

                val ctx = MappingContext(colors, texts, templates)
                val components = mutableListOf<Component>()
                val mappingErrors = mutableListOf<ErrorContentRaw>()

                raws.forEach {
                    runCatching {
                        components += it.toDomain(ctx)
                    }.getOrElse { error ->
                        log.error("Ошибка маппинга компонента: ${it.base.id}", error)
                        mappingErrors += ErrorContentRaw.emerge("Ошибка маппинга компонента id='${it.base.id}")
                    }
                }

                if (mappingErrors.isNotEmpty()) BatchValidationOutcome.Error(mappingErrors)
                else BatchValidationOutcome.Success(components)
            }
            .onErrorResume { e ->
                BatchValidationOutcome.Error(listOf(ErrorContentRaw.emerge("Ошибка валидации/маппинга: ${e.message}")))
                    .toMono()
            }
    }

    fun validateAndMap(raw: ComponentRaw): Mono<ValidationOutcome> =
        validateAndMapAll(listOf(raw))
            .map { outcome ->
                when (outcome) {
                    is BatchValidationOutcome.Success -> ValidationOutcome.Success(outcome.components.first())
                    is BatchValidationOutcome.Error -> ValidationOutcome.Error(outcome.errors)
                }
            }
}