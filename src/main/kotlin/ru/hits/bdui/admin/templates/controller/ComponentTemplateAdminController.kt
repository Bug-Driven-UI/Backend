package ru.hits.bdui.admin.templates.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.templates.ColorStyleService
import ru.hits.bdui.admin.templates.controller.raw.ColorStyleFromRawMapper
import ru.hits.bdui.admin.templates.controller.raw.ColorStyleListResponseRaw
import ru.hits.bdui.admin.templates.controller.raw.ColorStyleRaw
import ru.hits.bdui.admin.templates.controller.raw.ColorStyleResponseRaw
import ru.hits.bdui.admin.templates.controller.raw.delete.ColorStyleDeleteRequestRaw
import ru.hits.bdui.admin.templates.controller.raw.delete.ColorStyleDeleteResponseRaw
import ru.hits.bdui.admin.templates.controller.raw.get.ColorStyleGetByTokenRequestRaw
import ru.hits.bdui.admin.templates.controller.raw.get.ColorStyleGetRequestRaw
import ru.hits.bdui.admin.templates.controller.raw.of
import ru.hits.bdui.admin.templates.controller.raw.save.ColorStyleSaveRequestRaw
import ru.hits.bdui.admin.templates.controller.raw.update.ColorStyleUpdateRequestRaw
import ru.hits.bdui.admin.templates.TemplateService
import ru.hits.bdui.common.models.api.ApiResponse

@RestController
class ComponentTemplateAdminController(
    private val service: TemplateService,
) {
    @PostMapping("/v1/template/save")
    fun save(@RequestBody request: ColorStyleSaveRequestRaw): Mono<ApiResponse<ColorStyleResponseRaw>> =
        Mono.fromCallable { ColorStyleFromRawMapper.ColorStyle(request.data.template) }
            .flatMap { template -> service.save(template) }
            .map { ColorStyleRaw.of(it) }
            .map { ApiResponse.success(ColorStyleResponseRaw(it)) }

    @PutMapping("/v1/template/update")
    fun update(@RequestBody request: ColorStyleUpdateRequestRaw): Mono<ApiResponse<ColorStyleResponseRaw>> =
        Mono.fromCallable { ColorStyleFromRawMapper.ColorStyleFromDatabase(request.data.id, request.data.template) }
            .flatMap { template -> service.update(template) }
            .map { ColorStyleRaw.of(it) }
            .map { ApiResponse.success(ColorStyleResponseRaw(it)) }

    @DeleteMapping("/v1/template/delete")
    fun delete(@RequestBody request: ColorStyleDeleteRequestRaw): Mono<ApiResponse<ColorStyleDeleteResponseRaw>> =
        service.delete(request.data.id)
            .map { ColorStyleDeleteResponseRaw("Текстовый стиль с id: ${request.data.id} успешно удален") }
            .map { ApiResponse.success(it) }

    @PostMapping("/v1/template/get")
    fun get(@RequestBody request: ColorStyleGetRequestRaw): Mono<ApiResponse<ColorStyleResponseRaw>> =
        service.findById(request.data.id)
            .map { ColorStyleRaw.of(it) }
            .map { ApiResponse.success(ColorStyleResponseRaw(it)) }


    @PostMapping("/v1/template/getByToken")
    fun getByToken(@RequestBody request: ColorStyleGetByTokenRequestRaw): Mono<ApiResponse<ColorStyleListResponseRaw>> =
        service.findAllLikeToken(request.data.query)
            .map { list -> list.map { ColorStyleRaw.of(it) } }
            .map { ApiResponse.success(ColorStyleListResponseRaw(it)) }
}