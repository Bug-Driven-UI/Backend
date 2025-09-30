package ru.hits.bdui.admin.screen.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.screen.ScreenValidationOutcome
import ru.hits.bdui.admin.screen.ScreenValidationService
import ru.hits.bdui.admin.screen.controller.raw.ScreenFromDatabaseRaw
import ru.hits.bdui.admin.screen.controller.raw.ScreenRaw
import ru.hits.bdui.admin.screen.controller.raw.get.GetVersionsResponseRaw
import ru.hits.bdui.admin.screen.controller.raw.update.ScreenUpdateRequestRaw
import ru.hits.bdui.common.models.admin.entity.screen.ScreenEntity
import ru.hits.bdui.common.models.api.ApiResponse
import ru.hits.bdui.utils.doOnNextWithMeasure

@RestController
class ScreenAdminController(
    private val validationService: ScreenValidationService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/v1/screen/getByName")
    fun getByName(@RequestBody entity: ScreenEntity): Mono<ApiResponse<ScreenFromDatabaseRaw>> {
    }

    @PostMapping("/v1/screen/getVersions")
    fun getVersions(@RequestBody entity: ScreenEntity): Mono<GetVersionsResponseRaw> {
    }

    @PostMapping("/v1/screen/setProductionVersion")
    fun setProductionVersion(@RequestBody entity: ScreenEntity): Mono<ApiResponse<ScreenFromDatabaseRaw>> {
    }

    @PostMapping("/v1/screen/get")
    fun get(@RequestBody entity: ScreenEntity): Mono<ApiResponse<ScreenFromDatabaseRaw>> {
    }

    @PutMapping("/v1/screen/save")
    fun save(@RequestBody screenForSaveRaw: ScreenRaw): Mono<ApiResponse<ScreenFromDatabaseRaw>> =
        validationService.validateAndMap(screenForSaveRaw)
            .flatMap { outcome ->
                when (outcome) {
                    is ScreenValidationOutcome.Success -> TODO()
                    is ScreenValidationOutcome.Error ->
                        Mono.just<ApiResponse<ScreenFromDatabaseRaw>>(ApiResponse.error(outcome.errors))
                }
            }
            .doOnNextWithMeasure { duration, result ->
                when (result) {
                    is ApiResponse.Success -> log.info("Экран успешно сохранен за {} мс", duration.toMillis())
                    is ApiResponse.Error -> log.error(
                        "При сохранении экрана произошла ошибка. Время выполнения {} мс",
                        duration.toMillis()
                    )
                }
            }

    @PostMapping("/v1/screen/update")
    fun update(@RequestBody request: ScreenUpdateRequestRaw): Mono<ApiResponse<ScreenFromDatabaseRaw>> =
        validationService.validateAndMap(request.screen)
            .flatMap { outcome ->
                when (outcome) {
                    is ScreenValidationOutcome.Success -> TODO()
                    is ScreenValidationOutcome.Error ->
                        Mono.just<ApiResponse<ScreenFromDatabaseRaw>>(ApiResponse.error(outcome.errors))
                }
            }
            .doOnNextWithMeasure { duration, result ->
                when (result) {
                    is ApiResponse.Success -> log.info("Экран успешно обновлен за {} мс", duration.toMillis())
                    is ApiResponse.Error -> log.error(
                        "При обновлении экрана произошла ошибка. Время выполнения {} мс",
                        duration.toMillis()
                    )
                }
            }
}