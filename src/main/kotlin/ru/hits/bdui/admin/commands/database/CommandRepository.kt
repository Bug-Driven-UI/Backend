package ru.hits.bdui.admin.commands.database

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.admin.commands.database.CommandRepository.FindAllResponse
import ru.hits.bdui.admin.commands.database.CommandRepository.FindResponse
import ru.hits.bdui.admin.commands.database.CommandRepository.SaveResponse
import ru.hits.bdui.admin.commands.database.emerge.emerge
import ru.hits.bdui.admin.commands.database.entity.CommandEntity
import ru.hits.bdui.admin.commands.database.repository.CommandJpaRepository
import ru.hits.bdui.admin.commands.models.UpdateCommand
import ru.hits.bdui.domain.CommandName
import ru.hits.bdui.domain.command.Command
import ru.hits.bdui.domain.command.CommandFromDatabase
import java.util.UUID

interface CommandRepository {
    fun findById(id: UUID): Mono<FindResponse>

    fun findByName(name: CommandName): Mono<FindResponse>

    sealed interface FindResponse {
        data class Found(val command: CommandFromDatabase) : FindResponse
        data object NotFound : FindResponse
    }

    fun save(command: Command): Mono<SaveResponse>

    fun update(command: CommandFromDatabase, updateCommand: UpdateCommand): Mono<SaveResponse>

    sealed interface SaveResponse {
        data class Success(val command: CommandFromDatabase) : SaveResponse
        data class Error(val error: Throwable) : SaveResponse
    }

    fun delete(id: UUID): Mono<Unit>

    fun findAllLikeName(name: CommandName): Mono<FindAllResponse>

    sealed interface FindAllResponse {
        data class Success(val commands: List<CommandFromDatabase>) : FindAllResponse
        data class Error(val error: Throwable) : FindAllResponse
    }

    fun existsById(id: UUID): Mono<Boolean>
}

@Component
class CommandRepositoryImpl(
    private val repository: CommandJpaRepository
) : CommandRepository {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional(readOnly = true)
    override fun findById(id: UUID): Mono<FindResponse> =
        Mono.fromCallable { repository.findById(id) }
            .map { result ->
                if (result.isPresent) {
                    val command = CommandFromDatabase.emerge(result.get())
                    FindResponse.Found(command)
                } else {
                    FindResponse.NotFound
                }
            }
            .doOnError { error -> log.error("При получении команды произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun findByName(name: CommandName): Mono<FindResponse> =
        Mono.fromCallable { repository.findByName(name.value) }
            .map { result ->
                if (result.isPresent) {
                    val command = CommandFromDatabase.emerge(result.get())
                    FindResponse.Found(command)
                } else {
                    FindResponse.NotFound
                }
            }
            .doOnError { error -> log.error("При получении команды по названию произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional
    override fun save(command: Command): Mono<SaveResponse> {
        val entity = CommandEntity.emerge(command)

        return save(entity)
    }

    @Transactional
    override fun update(command: CommandFromDatabase, updateCommand: UpdateCommand): Mono<SaveResponse> {
        val entity = CommandEntity.emerge(command, updateCommand)

        return save(entity)
    }

    @Transactional
    override fun delete(id: UUID): Mono<Unit> =
        Mono.fromCallable { repository.deleteById(id) }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun findAllLikeName(name: CommandName): Mono<FindAllResponse> =
        Mono.fromCallable { repository.findAllLikeName(name.value) }
            .map { list -> list.map(CommandFromDatabase::emerge) }
            .map<FindAllResponse>(FindAllResponse::Success)
            .doOnError { error -> log.error("При получении команд по имени произошла ошибка", error) }
            .onErrorResume { FindAllResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())

    @Transactional(readOnly = true)
    override fun existsById(id: UUID): Mono<Boolean> =
        Mono.fromCallable { repository.existsById(id) }
            .doOnError { error -> log.error("При проверке наличия команды произошла ошибка", error) }
            .subscribeOn(Schedulers.boundedElastic())

    private fun save(entity: CommandEntity): Mono<SaveResponse> =
        Mono.fromCallable { repository.save(entity) }
            .map(CommandFromDatabase::emerge)
            .map<SaveResponse>(SaveResponse::Success)
            .doOnError { error -> log.error("При сохранении команды произошла ошибка", error) }
            .onErrorResume { SaveResponse.Error(it).toMono() }
            .subscribeOn(Schedulers.boundedElastic())
}