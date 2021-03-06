package ru.wearemad.mad_base.state_machine

sealed class DataAction<out T : Any> {

    /**
     * Отправляется для загрузки данных, нет кэша
     */
    object LoadHasNoCache : DataAction<Nothing>()

    /**
     * Отправляется для загрузки данных, есть кэш
     */
    data class LoadHasCache<T : Any>(val data: T) : DataAction<T>()

    /**
     * Отправляется для обновления
     */
    object Refresh : DataAction<Nothing>()

    /**
     * Отправляется, когда данные загрузились
     */
    data class NewData<T : Any>(val data: T) : DataAction<T>()

    /**
     * Отправляется, когда данные есть, но они пустые
     */
    object NewEmptyData : DataAction<Nothing>()

    /**
     * Отправляется при ошибке загрузки данных
     */
    data class LoadingError(val error: Throwable) : DataAction<Nothing>()

    override fun toString(): String = javaClass.simpleName
}