package ru.wearemad.mad_base.data_wrapper.checkable

import ru.wearemad.mad_base.data_wrapper.DataWrapperInterface

/**
 * Интерфейс сущности, если объект может быть выделяемым
 */
interface CheckableDataInterface {

    var isChecked: Boolean

    fun toggleChecked() {
        isChecked = !isChecked
    }
}

/**
 * Поддерживает множество выделений, через расширение коллекций
 * Если необходимо одиночное выделение -> смотри [SelectableData]
 */
data class CheckableData<T : Any?>(
    override var data: T,
    override var isChecked: Boolean = false
) : DataWrapperInterface<T>,
    CheckableDataInterface