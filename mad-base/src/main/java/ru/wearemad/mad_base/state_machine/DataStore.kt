package ru.wearemad.mad_base.state_machine

import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class DataStore<T : Any> {

    private val stateMutableFlow = MutableStateFlow<DataState<T>>(DataState.Initial)
    private val sideEffectChannel = Channel<DataSideEffect>()

    private val reducer = DataStateReducer<T>(sideEffectChannel)

    var state: DataState<T> = DataState.Initial
        private set

    val stateFlow: Flow<DataState<T>>
        get() = stateMutableFlow

    val sideEffectFlow: Flow<DataSideEffect>
        get() = sideEffectChannel.receiveAsFlow()

    suspend fun onNewAction(action: DataAction<T>) {
        val newState = reducer.reduce(state, action)
        Log.d("MIINE", "current state: $state, action: $action, new state: $newState")
        if (newState != state) {
            state = newState
            stateMutableFlow.value = state
        }
    }
}