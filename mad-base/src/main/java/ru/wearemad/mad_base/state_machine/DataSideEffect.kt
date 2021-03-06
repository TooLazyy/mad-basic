package ru.wearemad.mad_base.state_machine

sealed class DataSideEffect {

    /**
     * ивент, который отправляет state machine для иниуиализации загрузки данных
     */
    object LoadData : DataSideEffect()

    override fun toString(): String = javaClass.simpleName
}