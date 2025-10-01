package ru.hits.bdui.admin.commands

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.hits.bdui.admin.commands.database.CommandRepository
import ru.hits.bdui.admin.commands.models.UpdateCommand
import ru.hits.bdui.common.exceptions.AlreadyExistsException
import ru.hits.bdui.common.exceptions.notFound
import ru.hits.bdui.domain.CommandName
import ru.hits.bdui.domain.command.Command
import ru.hits.bdui.domain.command.CommandFromDatabase
import ru.hits.bdui.domain.template.ComponentTemplateFromDatabase
import ru.hits.bdui.utils.isUniqueViolation
import java.util.UUID

interface CommandService {
    fun save(command: Command): Mono<CommandFromDatabase>
    fun findById(id: UUID): Mono<CommandFromDatabase>
    fun findByName(name: CommandName): Mono<CommandFromDatabase>
    fun delete(id: UUID): Mono<Unit>
    fun update(updateCommand: UpdateCommand): Mono<CommandFromDatabase>
    fun findAllLikeName(name: CommandName): Mono<List<CommandFromDatabase>>
}

@Component
class CommandServiceImpl(
    private val repository: CommandRepository
) : CommandService {
    @Transactional
    override fun save(command: Command): Mono<CommandFromDatabase> =
        repository.save(command)
            .handleSaveResponse()

    @Transactional(readOnly = true)
    override fun findById(id: UUID): Mono<CommandFromDatabase> =
        repository.findById(id)
            .map { response ->
                when (response) {
                    is CommandRepository.FindResponse.Found ->
                        response.command

                    is CommandRepository.FindResponse.NotFound ->
                        throw notFound<CommandFromDatabase>(id)
                }
            }

    @Transactional(readOnly = true)
    override fun findByName(name: CommandName): Mono<CommandFromDatabase> =
        repository.findByName(name)
            .map { response ->
                when (response) {
                    is CommandRepository.FindResponse.Found ->
                        response.command

                    is CommandRepository.FindResponse.NotFound ->
                        throw notFound<CommandFromDatabase>(name.value)
                }
            }

    @Transactional
    override fun delete(id: UUID): Mono<Unit> =
        repository.delete(id)

    @Transactional
    override fun update(updateCommand: UpdateCommand): Mono<CommandFromDatabase> =
        repository.findById(updateCommand.id)
            .flatMap { response ->
                when (response) {
                    is CommandRepository.FindResponse.Found ->
                        repository.update(response.command, updateCommand)

                    is CommandRepository.FindResponse.NotFound ->
                        throw notFound<ComponentTemplateFromDatabase>(updateCommand.id)
                }
            }
            .handleSaveResponse()

    @Transactional(readOnly = true)
    override fun findAllLikeName(name: CommandName): Mono<List<CommandFromDatabase>> =
        repository.findAllLikeName(name)
            .map { response ->
                when (response) {
                    is CommandRepository.FindAllResponse.Success ->
                        response.commands

                    is CommandRepository.FindAllResponse.Error ->
                        throw response.error
                }
            }

    private fun Mono<CommandRepository.SaveResponse>.handleSaveResponse(): Mono<CommandFromDatabase> =
        this
            .map { response ->
                when (response) {
                    is CommandRepository.SaveResponse.Success ->
                        response.command

                    is CommandRepository.SaveResponse.Error ->
                        if (response.error.isUniqueViolation())
                            throw AlreadyExistsException("Команда с таким названием уже существует")
                        else
                            throw response.error
                }
            }
}