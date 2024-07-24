package com.sun5066.core.mvi

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Container<STATE : Any, SIDE_EFFECT : Any> {
    val stateFlow: StateFlow<STATE>
    val sideEffect: Flow<SIDE_EFFECT>

    fun intent(transformer: suspend ContainerContext<STATE, SIDE_EFFECT>.() -> Unit): Job
}