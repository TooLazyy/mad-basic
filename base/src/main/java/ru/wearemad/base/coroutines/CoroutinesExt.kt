package ru.wearemad.base.coroutines

import kotlinx.coroutines.channels.SendChannel
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

inline fun <T : Any?> wrapResult(block: () -> T): RequestResult<T> {
    return try {
        RequestResult.Success(block())
    } catch (ex: Exception) {
        RequestResult.Error(ex)
    }
}

suspend inline fun <T : Any?> wrapContinuationResult(
    crossinline block: Continuation<RequestResult<T>>.() -> Unit
): RequestResult<T> = suspendCoroutine { cont ->
    try {
        cont.block()
    } catch (ex: Exception) {
        cont.resume(RequestResult.Error(ex))
    }
}

suspend fun <R : Any?> RequestResult<R>.mapError(mapper: suspend (Throwable) -> Throwable): RequestResult<R> =
    when (this) {
        is RequestResult.Error -> RequestResult.Error(mapper(error))
        is RequestResult.Success -> this
    }

suspend fun <T : Any?, R : Any?> RequestResult<T>.map(mapper: suspend (T) -> R): RequestResult<R> =
    when (this) {
        is RequestResult.Error -> RequestResult.Error(error)
        is RequestResult.Success -> RequestResult.Success(mapper(data))
    }

suspend fun <T : Any?, R : Any?> RequestResult<T>.mapResult(mapper: suspend (T) -> RequestResult<R>): RequestResult<R> =
    when (this) {
        is RequestResult.Error -> RequestResult.Error(error)
        is RequestResult.Success -> mapper(data)
    }

fun <T : Any?> RequestResult<T>.resultOrDefault(default: T): T =
    when (this) {
        is RequestResult.Error -> default
        is RequestResult.Success -> data
    }

suspend fun <T : Any?> SendChannel<T>.trySend(message: T) {
    try {
        send(message)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun <T : Any?> SendChannel<T>.tryOffer(message: T) {
    try {
        offer(message)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}