package ru.hits.bdui.common.components

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.colorStyles.database.ColorStyleRepository
import ru.hits.bdui.admin.templates.database.TemplateRepository
import ru.hits.bdui.admin.textStyles.database.TextStyleRepository
import ru.hits.bdui.common.components.validation.MappingContext
import ru.hits.bdui.common.components.validation.NeededRefsCollector
import ru.hits.bdui.common.components.validation.RepeatedIdChecker
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

@Service
class ComponentValidationService(
    private val refsCollector: NeededRefsCollector,
    private val repeatedIdChecker: RepeatedIdChecker,
    private val colorStyleRepository: ColorStyleRepository,
    private val textStyleRepository: TextStyleRepository,
    private val templateRepository: TemplateRepository,
) {
    fun validateAndMap(raw: ComponentRaw): Mono<ValidationOutcome> {
        val needed = refsCollector.collect(raw)

        val colorsMono: Mono<Map<String, ColorStyle>> =
            if (needed.colorTokens.isEmpty()) {
                emptyMap<String, ColorStyle>().toMono()
            } else {
                colorStyleRepository.findAllByTokens(needed.colorTokens)
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
            if (needed.textTokens.isEmpty()) {
                emptyMap<String, TextStyle>().toMono()
            } else {
                textStyleRepository.findAllByTokens(needed.textTokens)
                    .map { response ->
                        when (response) {
                            is TextStyleRepository.FindAllResponse.Success ->
                                response.textStyles.associateBy({ it.textStyle.token }) { it.textStyle }

                            is TextStyleRepository.FindAllResponse.Error -> throw response.error
                        }
                    }
            }

        val templatesMono: Mono<Map<String, ComponentTemplate>> =
            if (needed.templateNames.isEmpty()) {
                emptyMap<String, ComponentTemplate>().toMono()
            } else {
                templateRepository.findAllByNameIn(needed.templateNames)
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
                val repeatedIds = repeatedIdChecker.checkIds(raw)

                val errors = mutableListOf<ErrorContentRaw>()

                repeatedIds.forEach { id ->
                    errors += ErrorContentRaw.emerge("В компоненте содержится повторяющийся id='$id'")
                }

                (needed.colorTokens - colors.keys).forEach {
                    errors += ErrorContentRaw.emerge("Не найден цвет по токену='$it'")
                }
                (needed.textTokens - texts.keys).forEach {
                    errors += ErrorContentRaw.emerge("Не найден стиль текста по токену='$it'")
                }
                (needed.templateNames - templates.keys).forEach {
                    errors += ErrorContentRaw.emerge("Не найден шаблон компонента по имени='$it'")
                }

                if (errors.isNotEmpty()) {
                    return@map ValidationOutcome.Error(errors)
                }

                val ctx = MappingContext(colors, texts, templates)
                val domain: Component = raw.toDomain(ctx)
                ValidationOutcome.Success(domain)
            }
            .onErrorResume { e ->
                ValidationOutcome.Error(listOf(ErrorContentRaw.emerge("Ошибка валидации/маппинга: ${e.message}")))
                    .toMono()
            }
    }
}