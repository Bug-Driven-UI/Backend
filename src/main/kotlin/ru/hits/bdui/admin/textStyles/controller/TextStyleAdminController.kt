package ru.hits.bdui.admin.textStyles.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.textStyles.TextStyleService
import ru.hits.bdui.admin.textStyles.controller.raw.TextStyleFromRawMapper
import ru.hits.bdui.admin.textStyles.controller.raw.TextStyleListResponseRaw
import ru.hits.bdui.admin.textStyles.controller.raw.TextStyleRaw
import ru.hits.bdui.admin.textStyles.controller.raw.TextStyleResponseRaw
import ru.hits.bdui.admin.textStyles.controller.raw.delete.TextStyleDeleteRequestRaw
import ru.hits.bdui.admin.textStyles.controller.raw.delete.TextStyleDeleteResponseSuccessRaw
import ru.hits.bdui.admin.textStyles.controller.raw.get.TextStyleGetByTokenRequestRaw
import ru.hits.bdui.admin.textStyles.controller.raw.get.TextStyleGetRequestRaw
import ru.hits.bdui.admin.textStyles.controller.raw.of
import ru.hits.bdui.admin.textStyles.controller.raw.save.TextStyleSaveRequestRaw
import ru.hits.bdui.admin.textStyles.controller.raw.update.TextStyleUpdateRequestRaw
import ru.hits.bdui.common.models.api.ApiResponse
import ru.hits.bdui.utils.doOnNextWithMeasure

@RestController
class TextStyleAdminController(
    private val service: TextStyleService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/v1/textStyle/save")
    fun save(@RequestBody request: TextStyleSaveRequestRaw): Mono<ApiResponse<TextStyleResponseRaw>> =
        Mono.fromCallable { TextStyleFromRawMapper.TextStyle(request.data.textStyle) }
            .flatMap { textStyle -> service.save(textStyle) }
            .doOnNextWithMeasure { duration, _ ->
                log.info("Стиль текста сохранен за {} мс", duration.toMillis())
            }
            .map { TextStyleRaw.of(it) }
            .map { ApiResponse.success(TextStyleResponseRaw(it)) }

    @PutMapping("/v1/textStyle/update")
    fun update(@RequestBody request: TextStyleUpdateRequestRaw): Mono<ApiResponse<TextStyleResponseRaw>> =
        Mono.fromCallable { TextStyleFromRawMapper.TextStyleFromDatabase(request.data.id, request.data.textStyle) }
            .flatMap { textStyle -> service.update(textStyle) }
            .doOnNextWithMeasure { duration, _ ->
                log.info("Стиль текста обновлен за {} мс", duration.toMillis())
            }
            .map { TextStyleRaw.of(it) }
            .map { ApiResponse.success(TextStyleResponseRaw(it)) }

    @DeleteMapping("/v1/textStyle/delete")
    fun delete(@RequestBody request: TextStyleDeleteRequestRaw): Mono<ApiResponse<TextStyleDeleteResponseSuccessRaw>> =
        service.delete(request.data.id)
            .doOnNextWithMeasure { duration, _ ->
                log.info("Стиль текста удален за {} мс", duration.toMillis())
            }
            .map { TextStyleDeleteResponseSuccessRaw("Текстовый стиль с id: ${request.data.id} успешно удален") }
            .map { ApiResponse.success(it) }

    @PostMapping("/v1/textStyle/get")
    fun get(@RequestBody request: TextStyleGetRequestRaw): Mono<ApiResponse<TextStyleResponseRaw>> =
        service.findById(request.data.id)
            .doOnNextWithMeasure { duration, _ ->
                log.info("Стиль текста найден за {} мс", duration.toMillis())
            }
            .map { TextStyleRaw.of(it) }
            .map { ApiResponse.success(TextStyleResponseRaw(it)) }


    @PostMapping("/v1/textStyle/getByToken")
    fun getByToken(@RequestBody request: TextStyleGetByTokenRequestRaw): Mono<ApiResponse<TextStyleListResponseRaw>> =
        service.findAllLikeToken(request.data.query)
            .doOnNextWithMeasure { duration, _ ->
                log.info("Стили текста найдены за {} мс", duration.toMillis())
            }
            .map { list -> list.map { TextStyleRaw.of(it) } }
            .map { ApiResponse.success(TextStyleListResponseRaw(it)) }
}