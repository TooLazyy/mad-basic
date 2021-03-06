package ru.wearemad.mad_base.data_wrapper.selectable

import ru.wearemad.mad_base.data_wrapper.DataWrapperInterface

interface SelectableDataInterface {

    var isSelected: Boolean

    fun toggleSelected() {
        isSelected = !isSelected
    }
}

data class SelectableData<T : Any?>(
    override var data: T,
    override var isSelected: Boolean = false
) : DataWrapperInterface<T>,
    SelectableDataInterface