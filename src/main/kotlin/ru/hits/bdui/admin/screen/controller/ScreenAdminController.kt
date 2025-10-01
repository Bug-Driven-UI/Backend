package ru.hits.bdui.admin.screen.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.screen.ScreenService
import ru.hits.bdui.admin.screen.ScreenValidationOutcome
import ru.hits.bdui.admin.screen.ScreenValidationService
import ru.hits.bdui.admin.screen.controller.raw.ScreenFromDatabaseRaw
import ru.hits.bdui.admin.screen.controller.raw.ScreenRaw
import ru.hits.bdui.admin.screen.controller.raw.ScreenVersionRaw
import ru.hits.bdui.admin.screen.controller.raw.get.GetByNameRequestRaw
import ru.hits.bdui.admin.screen.controller.raw.get.GetByNameResponseRaw
import ru.hits.bdui.admin.screen.controller.raw.get.GetScreenRequestRaw
import ru.hits.bdui.admin.screen.controller.raw.get.GetVersionsRequestRaw
import ru.hits.bdui.admin.screen.controller.raw.get.GetVersionsResponseRaw
import ru.hits.bdui.admin.screen.controller.raw.update.ScreenUpdateRequestRaw
import ru.hits.bdui.admin.screen.controller.raw.update.SetProductionVersionRequestRaw
import ru.hits.bdui.admin.screen.controller.raw.utils.emerge
import ru.hits.bdui.admin.screen.models.ScreenUpdateCommand
import ru.hits.bdui.common.models.api.ApiResponse
import ru.hits.bdui.domain.ScreenId
import ru.hits.bdui.domain.ScreenName
import ru.hits.bdui.utils.doOnNextWithMeasure

@RestController
class ScreenAdminController(
    private val validationService: ScreenValidationService,
    private val screenService: ScreenService,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/v1/screen/getByName")
    fun getByName(@RequestBody request: GetByNameRequestRaw): Mono<ApiResponse<GetByNameResponseRaw>> =
        screenService.findAllLikeName(ScreenName(request.data.query))
            .map { list ->
                GetByNameResponseRaw(
                    screenNames = list.map {
                        GetByNameResponseRaw.ScreenNameRaw(
                            id = it.id.value,
                            name = it.name.value,
                            description = it.description
                        )
                    }
                )
            }
            .map { ApiResponse.success(it) }

    @PostMapping("/v1/screen/getVersions")
    fun getVersions(@RequestBody request: GetVersionsRequestRaw): Mono<ApiResponse<GetVersionsResponseRaw>> =
        screenService.findAllVersions(ScreenId(request.data.screenId))
            .map { list ->
                GetVersionsResponseRaw(
                    list.map {
                        ScreenVersionRaw.emerge(it)
                    }
                )
            }
            .doOnNextWithMeasure { duration, _ ->
                log.info("Версии получены за {} мс", duration.toMillis())
            }
            .map { ApiResponse.success(it) }

    @PostMapping("/v1/screen/setProductionVersion")
    fun setProductionVersion(@RequestBody request: SetProductionVersionRequestRaw): Mono<ApiResponse<ScreenVersionRaw>> =
        screenService.setProductionVersion(
            screenId = ScreenId(request.data.screenId),
            versionId = request.data.versionId
        )
            .map { ScreenVersionRaw.emerge(it) }
            .doOnNextWithMeasure { duration, _ ->
                log.info("Версия прода установлена за {} мс", duration.toMillis())
            }
            .map { ApiResponse.success(it) }

    @PostMapping("/v1/screen/get")
    fun get(@RequestBody request: GetScreenRequestRaw): Mono<ApiResponse<ScreenFromDatabaseRaw>> =
        screenService.find(
            screenId = ScreenId(request.data.screenId),
            versionId = request.data.versionId
        )
            .doOnNextWithMeasure { duration, _ ->
                log.info("Экран получен за {} мс", duration.toMillis())
            }
            .map { ScreenFromDatabaseRaw.emerge(it) }
            .map { ApiResponse.success(it) }

    @PostMapping("/v1/screen/save")
    fun save(@RequestBody screenForSaveRaw: ScreenRaw): Mono<ApiResponse<ScreenFromDatabaseRaw>> =
        validationService.validateAndMap(screenForSaveRaw)
            .flatMap { outcome ->
                when (outcome) {
                    is ScreenValidationOutcome.Success ->
                        screenService.save(outcome.screen)
                            .map { ScreenFromDatabaseRaw.emerge(it) }
                            .map { ApiResponse.success(it) }

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

    //TODO(НЕ РАБОТАЕТ)
    @PutMapping("/v1/screen/update")
    fun update(@RequestBody request: ScreenUpdateRequestRaw): Mono<ApiResponse<ScreenFromDatabaseRaw>> =
        validationService.validateAndMap(request.screen)
            .flatMap { outcome ->
                when (outcome) {
                    is ScreenValidationOutcome.Success ->
                        screenService.update(
                            ScreenUpdateCommand(
                                screenId = ScreenId(request.screenId),
                                versionId = request.versionId,
                                screen = outcome.screen
                            )
                        )
                            .map { ScreenFromDatabaseRaw.emerge(it) }
                            .map { ApiResponse.success(it) }

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