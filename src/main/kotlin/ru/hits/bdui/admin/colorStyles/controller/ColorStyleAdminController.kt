package ru.hits.bdui.admin.colorStyles.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.colorStyles.ColorStyleService
import ru.hits.bdui.admin.colorStyles.controller.raw.ColorStyleFromRawMapper
import ru.hits.bdui.admin.colorStyles.controller.raw.ColorStyleListResponseRaw
import ru.hits.bdui.admin.colorStyles.controller.raw.ColorStyleRaw
import ru.hits.bdui.admin.colorStyles.controller.raw.ColorStyleResponseRaw
import ru.hits.bdui.admin.colorStyles.controller.raw.delete.ColorStyleDeleteRequestRaw
import ru.hits.bdui.admin.colorStyles.controller.raw.delete.ColorStyleDeleteResponseRaw
import ru.hits.bdui.admin.colorStyles.controller.raw.get.ColorStyleGetByTokenRequestRaw
import ru.hits.bdui.admin.colorStyles.controller.raw.get.ColorStyleGetRequestRaw
import ru.hits.bdui.admin.colorStyles.controller.raw.of
import ru.hits.bdui.admin.colorStyles.controller.raw.save.ColorStyleSaveRequestRaw
import ru.hits.bdui.admin.colorStyles.controller.raw.update.ColorStyleUpdateRequestRaw
import ru.hits.bdui.common.models.api.ApiResponse

@RestController
class ColorStyleAdminController(
    private val service: ColorStyleService
) {
    @PostMapping("/v1/colorStyle/save")
    fun save(@RequestBody request: ColorStyleSaveRequestRaw): Mono<ApiResponse<ColorStyleResponseRaw>> =
        Mono.fromCallable { ColorStyleFromRawMapper.ColorStyle(request.data.colorStyle) }
            .flatMap { colorStyle -> service.save(colorStyle) }
            .map { ColorStyleRaw.of(it) }
            .map { ApiResponse.success(ColorStyleResponseRaw(it)) }

    @PutMapping("/v1/colorStyle/update")
    fun update(@RequestBody request: ColorStyleUpdateRequestRaw): Mono<ApiResponse<ColorStyleResponseRaw>> =
        Mono.fromCallable { ColorStyleFromRawMapper.ColorStyleFromDatabase(request.data.id, request.data.colorStyle) }
            .flatMap { colorStyle -> service.update(colorStyle) }
            .map { ColorStyleRaw.of(it) }
            .map { ApiResponse.success(ColorStyleResponseRaw(it)) }

    @DeleteMapping("/v1/colorStyle/delete")
    fun delete(@RequestBody request: ColorStyleDeleteRequestRaw): Mono<ApiResponse<ColorStyleDeleteResponseRaw>> =
        service.delete(request.data.id)
            .map { ColorStyleDeleteResponseRaw("Текстовый стиль с id: ${request.data.id} успешно удален") }
            .map { ApiResponse.success(it) }

    @PostMapping("/v1/colorStyle/get")
    fun get(@RequestBody request: ColorStyleGetRequestRaw): Mono<ApiResponse<ColorStyleResponseRaw>> =
        service.findById(request.data.id)
            .map { ColorStyleRaw.of(it) }
            .map { ApiResponse.success(ColorStyleResponseRaw(it)) }


    @PostMapping("/v1/colorStyle/getByToken")
    fun getByToken(@RequestBody request: ColorStyleGetByTokenRequestRaw): Mono<ApiResponse<ColorStyleListResponseRaw>> =
        service.findAllLikeToken(request.data.query)
            .map { list -> list.map { ColorStyleRaw.of(it) } }
            .map { ApiResponse.success(ColorStyleListResponseRaw(it)) }
}