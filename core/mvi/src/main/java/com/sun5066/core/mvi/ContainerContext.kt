package com.sun5066.core.mvi

import kotlinx.coroutines.flow.StateFlow

data class ContainerContext<STATE : Any, SIDE_EFFECT : Any>(
    val postSideEffect: suspend (SIDE_EFFECT) -> Unit,
    val reduce: suspend ((STATE) -> STATE) -> Unit,
    val stateFlow: StateFlow<STATE>,
) {
    val state: STATE
        get() = stateFlow.value
}