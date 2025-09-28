package ru.hits.bdui.admin.templates.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.templates.TemplateService
import ru.hits.bdui.admin.templates.controller.raw.ComponentTemplateFromRawMapper
import ru.hits.bdui.admin.templates.controller.raw.ComponentTemplateListResponseRaw
import ru.hits.bdui.admin.templates.controller.raw.ComponentTemplateRaw
import ru.hits.bdui.admin.templates.controller.raw.ComponentTemplateResponseRaw
import ru.hits.bdui.admin.templates.controller.raw.delete.ComponentTemplateDeleteRequestRaw
import ru.hits.bdui.admin.templates.controller.raw.delete.ComponentTemplateDeleteResponseRaw
import ru.hits.bdui.admin.templates.controller.raw.get.ComponentTemplateGetByTokenRequestRaw
import ru.hits.bdui.admin.templates.controller.raw.get.ComponentTemplateGetRequestRaw
import ru.hits.bdui.admin.templates.controller.raw.of
import ru.hits.bdui.admin.templates.controller.raw.save.ComponentTemplateSaveRequestRaw
import ru.hits.bdui.admin.templates.controller.raw.update.ComponentTemplateUpdateRequestRaw
import ru.hits.bdui.common.models.api.ApiResponse

@RestController
class ComponentTemplateAdminController(
    private val service: TemplateService,
) {
    @PostMapping("/v1/template/save")
    fun save(@RequestBody request: ComponentTemplateSaveRequestRaw): Mono<ApiResponse<ComponentTemplateResponseRaw>> =
        Mono.fromCallable { ComponentTemplateFromRawMapper.ComponentTemplate(request.data.template) }
            .flatMap { template -> service.save(template) }
            .map { ComponentTemplateRaw.of(it) }
            .map { ApiResponse.success(ComponentTemplateResponseRaw(it)) }

    @PutMapping("/v1/template/update")
    fun update(@RequestBody request: ComponentTemplateUpdateRequestRaw): Mono<ApiResponse<ComponentTemplateResponseRaw>> =
        Mono.fromCallable {
            ComponentTemplateFromRawMapper.ComponentTemplateUpdateCommand(
                request.data.id,
                request.data.template
            )
        }
            .flatMap { template -> service.update(template) }
            .map { ComponentTemplateRaw.of(it) }
            .map { ApiResponse.success(ComponentTemplateResponseRaw(it)) }

    @DeleteMapping("/v1/template/delete")
    fun delete(@RequestBody request: ComponentTemplateDeleteRequestRaw): Mono<ApiResponse<ComponentTemplateDeleteResponseRaw>> =
        service.delete(request.data.id)
            .map { ComponentTemplateDeleteResponseRaw("Текстовый стиль с id: ${request.data.id} успешно удален") }
            .map { ApiResponse.success(it) }

    @PostMapping("/v1/template/get")
    fun get(@RequestBody request: ComponentTemplateGetRequestRaw): Mono<ApiResponse<ComponentTemplateResponseRaw>> =
        service.findById(request.data.id)
            .map { ComponentTemplateRaw.of(it) }
            .map { ApiResponse.success(ComponentTemplateResponseRaw(it)) }


    @PostMapping("/v1/template/getByName")
    fun getByToken(@RequestBody request: ComponentTemplateGetByTokenRequestRaw): Mono<ApiResponse<ComponentTemplateListResponseRaw>> =
        service.findAllLikeName(request.data.query)
            .map { list -> list.map { ComponentTemplateRaw.of(it) } }
            .map { ApiResponse.success(ComponentTemplateListResponseRaw(it)) }
}