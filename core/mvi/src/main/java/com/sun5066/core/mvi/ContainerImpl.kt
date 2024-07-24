package com.sun5066.core.mvi

import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ContainerImpl<STATE : Any, SIDE_EFFECT : Any>(
    initialState: STATE,
) : Container<STATE, SIDE_EFFECT> {

    private val _stateFlow = MutableStateFlow(initialState)
    private val _sideEffect = Channel<SIDE_EFFECT>(Channel.BUFFERED)

    override val stateFlow: StateFlow<STATE> = _stateFlow.asStateFlow()
    override val sideEffect: Flow<SIDE_EFFECT> = _sideEffect.receiveAsFlow()

    private val containerContext = ContainerContext(
        postSideEffect = _sideEffect::send,
        reduce = _stateFlow::update,
        stateFlow = stateFlow,
    )

    override fun intent(
        transformer: suspend ContainerContext<STATE, SIDE_EFFECT>.() -> Unit
    ): Job = runBlocking {
        launch { transformer.invoke(containerContext) }
    }
}