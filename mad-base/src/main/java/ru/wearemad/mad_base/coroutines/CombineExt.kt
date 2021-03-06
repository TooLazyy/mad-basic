package ru.wearemad.mad_base.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

@Suppress("UNCHECKED_CAST")
suspend fun <T1 : Any, T2 : Any, R : Any> combineResults(
    t1: RequestResult<T1>,
    t2: RequestResult<T2>,
    combiner: (T1, T2) -> R
): RequestResult<R> = coroutineScope {
    val result = awaitAll(
        async { t1 },
        async { t2 }
    )
    val failed = result.firstOrNull { it is RequestResult.Error } as? RequestResult.Error
    if (failed == null) {
        RequestResult.Success(
            combiner(
                (result[0] as RequestResult.Success<T1>).data,
                (result[1] as RequestResult.Success<T2>).data,
            )
        )
    } else {
        RequestResult.Error(failed.error)
    }
}