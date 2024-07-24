package com.sun5066.core.mvi

import com.sun5066.core.mvi.annoation.MviDsl

@MviDsl
class Syntax<STATE : Any, SIDE_EFFECT : Any>(
    private val containerContext: ContainerContext<STATE, SIDE_EFFECT>
) {
    val state: STATE get() = containerContext.state

    @MviDsl
    suspend fun reduce(reducer: IntentContext<STATE>.() -> STATE) {
        containerContext.reduce { IntentContext(it).reducer() }
    }

    @MviDsl
    suspend fun postSideEffect(sideEffect: SIDE_EFFECT) {
        containerContext.postSideEffect(sideEffect)
    }
}