package ru.hits.bdui.textStyles.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.common.models.raw.ErrorContentRaw
import ru.hits.bdui.textStyles.controller.raw.TextStyleFromRawMapper
import ru.hits.bdui.textStyles.controller.raw.TextStyleToRawMapper
import ru.hits.bdui.textStyles.controller.raw.delete.TextStyleDeleteRequestRaw
import ru.hits.bdui.textStyles.controller.raw.delete.TextStyleDeleteResponseErrorRaw
import ru.hits.bdui.textStyles.controller.raw.delete.TextStyleDeleteResponseRaw
import ru.hits.bdui.textStyles.controller.raw.delete.TextStyleDeleteResponseSuccessRaw
import ru.hits.bdui.textStyles.controller.raw.get.TextStyleGetByTokenRequestRaw
import ru.hits.bdui.textStyles.controller.raw.get.TextStyleGetRequestRaw
import ru.hits.bdui.textStyles.controller.raw.get.TextStyleGetResponseErrorRaw
import ru.hits.bdui.textStyles.controller.raw.get.TextStyleGetResponseRaw
import ru.hits.bdui.textStyles.controller.raw.get.TextStyleGetResponseSuccessRaw
import ru.hits.bdui.textStyles.controller.raw.save.TextStyleSaveRequestRaw
import ru.hits.bdui.textStyles.controller.raw.save.TextStyleSaveResponseErrorRaw
import ru.hits.bdui.textStyles.controller.raw.save.TextStyleSaveResponseRaw
import ru.hits.bdui.textStyles.controller.raw.save.TextStyleSaveResponseSuccessRaw
import ru.hits.bdui.textStyles.controller.raw.update.TextStyleUpdateRequestRaw
import ru.hits.bdui.textStyles.controller.raw.update.TextStyleUpdateResponseErrorRaw
import ru.hits.bdui.textStyles.controller.raw.update.TextStyleUpdateResponseRaw
import ru.hits.bdui.textStyles.controller.raw.update.TextStyleUpdateResponseSuccessRaw
import ru.hits.bdui.textStyles.database.TextStyleRepository

@RestController
class TextStyleAdminController(
    private val repository: TextStyleRepository
) {
    @PostMapping("/v1/textStyle/save")
    fun save(@RequestBody request: TextStyleSaveRequestRaw): Mono<TextStyleSaveResponseRaw> =
        Mono.fromCallable { TextStyleFromRawMapper.TextStyle(request.data.textStyle) }
            .flatMap { textStyle -> repository.save(textStyle) }
            .map { response ->
                when (response) {
                    is TextStyleRepository.SaveResponse.Success ->
                        TextStyleSaveResponseSuccessRaw(
                            TextStyleToRawMapper.TextStyleRaw(response.textStyle)
                        )

                    is TextStyleRepository.SaveResponse.Error ->
                        TextStyleSaveResponseErrorRaw(
                            errors = listOf(
                                ErrorContentRaw.emerge("Не удалось сохранить текстовый стиль")
                            )
                        )
                }
            }

    @PostMapping("/v1/textStyle/update")
    fun update(@RequestBody request: TextStyleUpdateRequestRaw): Mono<TextStyleUpdateResponseRaw> =
        Mono.fromCallable { TextStyleFromRawMapper.TextStyleFromDatabase(request.data.id, request.data.textStyle) }
            .flatMap { textStyle -> repository.update(textStyle) }
            .map { response ->
                when (response) {
                    is TextStyleRepository.SaveResponse.Success ->
                        TextStyleUpdateResponseSuccessRaw(
                            TextStyleToRawMapper.TextStyleRaw(response.textStyle)
                        )

                    is TextStyleRepository.SaveResponse.Error ->
                        TextStyleUpdateResponseErrorRaw(
                            errors = listOf(
                                ErrorContentRaw.emerge("Не удалось обновить текстовый стиль")
                            )
                        )
                }
            }

    @DeleteMapping("/v1/textStyle/delete")
    fun delete(@RequestBody request: TextStyleDeleteRequestRaw): Mono<TextStyleDeleteResponseRaw> =
        Mono.fromCallable { repository.delete(request.data.id) }
            .map<TextStyleDeleteResponseRaw> { TextStyleDeleteResponseSuccessRaw("Текстовый стиль с id: ${request.data.id} успешно удален") }
            .onErrorResume {
                TextStyleDeleteResponseErrorRaw(
                    errors = listOf(
                        ErrorContentRaw.emerge("Не удалось обновить текстовый стиль")
                    )
                ).toMono()
            }

    @PostMapping("/v1/textStyle/get")
    fun get(@RequestBody request: TextStyleGetRequestRaw): Mono<TextStyleGetResponseRaw> =
        Mono.fromCallable { repository.findById(request.data.id) }
            .map { response ->
                when (response) {
                    is TextStyleRepository.FindResponse.Found ->
                        TextStyleGetResponseSuccessRaw(
                            TextStyleToRawMapper.TextStyleRaw(response.textStyle)
                        )

                    is TextStyleRepository.FindResponse.NotFound ->
                        TextStyleGetResponseErrorRaw(
                            errors = listOf(
                                ErrorContentRaw.emerge("")
                            )
                        )
                }
            }


    @PostMapping("/v1/textStyle/getByToken")
    fun getByToken(@RequestBody request: TextStyleGetByTokenRequestRaw) {

    }
}