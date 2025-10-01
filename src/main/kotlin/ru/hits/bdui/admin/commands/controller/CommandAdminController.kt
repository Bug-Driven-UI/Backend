package ru.hits.bdui.admin.commands.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.commands.CommandService
import ru.hits.bdui.admin.commands.controller.raw.CommandFromDatabaseRaw
import ru.hits.bdui.admin.commands.controller.raw.CommandFromRawMapper
import ru.hits.bdui.admin.commands.controller.raw.CommandListResponseRaw
import ru.hits.bdui.admin.commands.controller.raw.CommandResponseRaw
import ru.hits.bdui.admin.commands.controller.raw.delete.CommandDeleteRequestRaw
import ru.hits.bdui.admin.commands.controller.raw.delete.CommandDeleteResponseRaw
import ru.hits.bdui.admin.commands.controller.raw.emerge
import ru.hits.bdui.admin.commands.controller.raw.get.CommandGetByNameRequestRaw
import ru.hits.bdui.admin.commands.controller.raw.get.CommandGetRequestRaw
import ru.hits.bdui.admin.commands.controller.raw.save.CommandSaveRequestRaw
import ru.hits.bdui.admin.commands.controller.raw.save.CommandSaveResponseRaw
import ru.hits.bdui.admin.commands.controller.raw.update.CommandUpdateRequestRaw
import ru.hits.bdui.admin.commands.controller.raw.update.CommandUpdateResponseRaw
import ru.hits.bdui.admin.templates.TemplateService
import ru.hits.bdui.common.models.api.ApiResponse
import ru.hits.bdui.domain.CommandName

@RestController
class CommandAdminController(
    private val service: CommandService,
    private val templateService: TemplateService
) {
    //TODO(Добавить валидацию на наличие API в бд)
    @PostMapping("/v1/command/save")
    fun save(@RequestBody request: CommandSaveRequestRaw): Mono<ApiResponse<CommandSaveResponseRaw>> {
        val templateMono = request.data.command.itemTemplateId?.let { templateService.findById(it) }

        return (templateMono?.map { CommandFromRawMapper.Command(request.data.command, it) }
            ?: Mono.fromCallable { CommandFromRawMapper.Command(request.data.command, null) })
            .flatMap { service.save(it) }
            .map { CommandFromDatabaseRaw.emerge(it) }
            .map { ApiResponse.success(CommandSaveResponseRaw(it)) }
    }

    @PutMapping("/v1/command/update")
    fun update(@RequestBody request: CommandUpdateRequestRaw): Mono<ApiResponse<CommandUpdateResponseRaw>> {
        val templateMono = request.data.command.itemTemplateId?.let { templateService.findById(it) }

        return (templateMono?.map { CommandFromRawMapper.UpdateCommand(request.data.id, request.data.command, it) }
            ?: Mono.fromCallable { CommandFromRawMapper.UpdateCommand(request.data.id, request.data.command, null) })
            .flatMap { service.update(it) }
            .map { CommandFromDatabaseRaw.emerge(it) }
            .map { ApiResponse.success(CommandUpdateResponseRaw(it)) }
    }

    @DeleteMapping("/v1/command/delete")
    fun delete(@RequestBody request: CommandDeleteRequestRaw): Mono<ApiResponse<CommandDeleteResponseRaw>> =
        service.delete(request.data.id)
            .map { CommandDeleteResponseRaw("Команда с id: ${request.data.id} успешно удалена") }
            .map { ApiResponse.success(it) }

    @PostMapping("/v1/command/get")
    fun get(@RequestBody request: CommandGetRequestRaw): Mono<ApiResponse<CommandResponseRaw>> =
        service.findById(request.data.id)
            .map { CommandFromDatabaseRaw.emerge(it) }
            .map { ApiResponse.success(CommandResponseRaw(it)) }

    @PostMapping("/v1/command/getByName")
    fun getByName(@RequestBody request: CommandGetByNameRequestRaw): Mono<ApiResponse<CommandListResponseRaw>> =
        service.findAllLikeName(CommandName(request.data.query))
            .map { list -> list.map(CommandFromDatabaseRaw::emerge) }
            .map { ApiResponse.success(CommandListResponseRaw(it)) }
}