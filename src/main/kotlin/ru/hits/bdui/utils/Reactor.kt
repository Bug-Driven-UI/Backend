package ru.hits.bdui.utils

import reactor.core.publisher.Mono
import java.time.Duration

fun <T> Mono<T>.doOnNextWithMeasure(
    onNext: (duration: Duration, element: T) -> Unit
): Mono<T> =
    this
        .materialize()
        .elapsed()
        .doOnNext {
            val evaluateTime = Duration.ofMillis(it.t1)
            val signal = it.t2
            if (signal.isOnNext)
                ignoreException {
                    onNext(evaluateTime, signal.get()!!)
                }
            it.t2
        }
        .map { it.t2 }
        .dematerialize()

private fun ignoreException(block: () -> Unit) {
    try {
        block()
    } catch (e: Exception) {
    }
}