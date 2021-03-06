package ru.wearemad.mad_base.state_machine

sealed class DataState<out T : Any> {

    /**
     * Начальное состояние
     */
    object Initial : DataState<Nothing>()

    /**
     * Нет данных, данные грузятся
     */
    object NoDataProgress : DataState<Nothing>()

    /**
     * Есть данные, данные грузятся
     */
    data class DataProgress<T : Any>(val data: T) : DataState<T>()

    /**
     * Есть данные
     */
    data class Data<T : Any>(val data: T) : DataState<T>()

    /**
     * Даныне загрузились успешно, но они пустые
     */
    object Empty : DataState<Nothing>()

    /**
     * Есть данные, данные обновляются, обновлено юзером (PTR или любой другой механизм)
     */
    data class DataRefresh<T : Any>(val data: T) : DataState<T>()

    /**
     * Есть данные, ошибка обновления
     */
    data class DataError<T : Any>(val data: T, val error: Throwable) : DataState<T>()

    /**
     * Нет данных, ошибка загрузки
     */
    data class NoDataError(val error: Throwable) : DataState<Nothing>()

    /**
     * Нет данных, ошибка загрузки, данные релоадятся
     */
    data class NoDataErrorProgress(val error: Throwable) : DataState<Nothing>()

    override fun toString(): String = javaClass.simpleName
}