package ru.wearemad.mad_base.state_machine

import kotlinx.coroutines.channels.Channel

class DataStateReducer<T : Any>(
    private val sideEffectChannel: Channel<DataSideEffect>
) {

    suspend fun reduce(
        currentState: DataState<T>,
        action: DataAction<T>
    ): DataState<T> = when (currentState) {
        is DataState.Initial -> reduceInitialState(action)
        is DataState.NoDataProgress -> reduceNoDataProgressState(currentState, action)
        is DataState.NoDataError -> reduceNoDataErrorState(currentState, action)
        is DataState.NoDataErrorProgress -> reduceNoDataErrorProgressState(currentState, action)
        is DataState.DataProgress -> reduceDataProgressState(currentState, action)
        is DataState.Data -> reduceDataState(currentState, action)
        is DataState.DataError -> reduceDataErrorState(currentState, action)
        is DataState.DataRefresh -> reduceDataRefreshState(currentState, action)
        is DataState.Empty -> reduceEmptyState(action)
    }

    private suspend fun reduceInitialState(action: DataAction<T>): DataState<T> = when (action) {
        is DataAction.Refresh -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.NoDataProgress
        }
        is DataAction.LoadHasNoCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.NoDataProgress
        }
        is DataAction.LoadHasCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.DataProgress(action.data)
        }
        is DataAction.LoadingError -> DataState.NoDataError(action.error)
        is DataAction.NewData -> DataState.Data(action.data)
        is DataAction.NewEmptyData -> DataState.Empty
    }

    private fun reduceNoDataProgressState(
        state: DataState.NoDataProgress,
        action: DataAction<T>
    ): DataState<T> = when (action) {
        is DataAction.Refresh -> state
        is DataAction.LoadHasNoCache -> state
        is DataAction.LoadHasCache -> DataState.DataProgress(action.data)
        is DataAction.LoadingError -> DataState.NoDataError(action.error)
        is DataAction.NewData -> DataState.Data(action.data)
        is DataAction.NewEmptyData -> DataState.Empty
    }

    private suspend fun reduceNoDataErrorState(
        state: DataState.NoDataError,
        action: DataAction<T>
    ): DataState<T> = when (action) {
        is DataAction.Refresh -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.NoDataErrorProgress(state.error)
        }
        is DataAction.LoadHasNoCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.NoDataErrorProgress(state.error)
        }
        is DataAction.LoadHasCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.DataProgress(action.data)
        }
        is DataAction.LoadingError -> DataState.NoDataError(action.error)
        is DataAction.NewData -> DataState.Data(action.data)
        is DataAction.NewEmptyData -> DataState.Empty
    }

    private fun reduceNoDataErrorProgressState(
        state: DataState.NoDataErrorProgress,
        action: DataAction<T>
    ): DataState<T> = when (action) {
        is DataAction.Refresh -> state
        is DataAction.LoadHasNoCache -> state
        is DataAction.LoadHasCache -> DataState.DataProgress(action.data)
        is DataAction.LoadingError -> DataState.NoDataError(action.error)
        is DataAction.NewData -> DataState.Data(action.data)
        is DataAction.NewEmptyData -> DataState.Empty
    }

    private fun reduceDataProgressState(
        state: DataState.DataProgress<T>,
        action: DataAction<T>
    ): DataState<T> = when (action) {
        is DataAction.Refresh -> DataState.DataRefresh(state.data)
        is DataAction.LoadHasNoCache -> DataState.DataRefresh(state.data)
        is DataAction.LoadHasCache<*> -> DataState.DataRefresh(state.data)
        is DataAction.LoadingError -> DataState.DataError(state.data, action.error)
        is DataAction.NewData -> DataState.Data(action.data)
        is DataAction.NewEmptyData -> DataState.Empty
    }

    private suspend fun reduceDataState(
        state: DataState.Data<T>,
        action: DataAction<T>
    ): DataState<T> = when (action) {
        is DataAction.Refresh -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.DataRefresh(state.data)
        }
        is DataAction.LoadHasNoCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.DataRefresh(state.data)
        }
        is DataAction.LoadHasCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.DataRefresh(action.data)
        }
        is DataAction.LoadingError -> DataState.DataError(state.data, action.error)
        is DataAction.NewData -> DataState.Data(action.data)
        is DataAction.NewEmptyData -> DataState.Empty
    }

    private suspend fun reduceDataErrorState(
        state: DataState.DataError<T>,
        action: DataAction<T>
    ): DataState<T> = when (action) {
        is DataAction.Refresh -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.DataRefresh(state.data)
        }
        is DataAction.LoadHasNoCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.DataRefresh(state.data)
        }
        is DataAction.LoadHasCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.DataRefresh(action.data)
        }
        is DataAction.LoadingError -> DataState.DataError(state.data, action.error)
        is DataAction.NewData -> DataState.Data(action.data)
        is DataAction.NewEmptyData -> DataState.Empty
    }

    private fun reduceDataRefreshState(
        state: DataState.DataRefresh<T>,
        action: DataAction<T>
    ): DataState<T> = when (action) {
        is DataAction.Refresh -> state
        is DataAction.LoadHasNoCache -> state
        is DataAction.LoadHasCache -> state
        is DataAction.LoadingError -> DataState.DataError(state.data, action.error)
        is DataAction.NewData -> DataState.Data(action.data)
        is DataAction.NewEmptyData -> DataState.Empty
    }

    private suspend fun reduceEmptyState(
        action: DataAction<T>
    ): DataState<T> = when (action) {
        is DataAction.Refresh -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.NoDataProgress
        }
        is DataAction.LoadHasNoCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.NoDataProgress
        }
        is DataAction.LoadHasCache -> {
            sideEffectChannel.send(DataSideEffect.LoadData)
            DataState.DataProgress(action.data)
        }
        is DataAction.LoadingError -> DataState.NoDataError(action.error)
        is DataAction.NewData -> DataState.Data(action.data)
        is DataAction.NewEmptyData -> DataState.Empty
    }
}