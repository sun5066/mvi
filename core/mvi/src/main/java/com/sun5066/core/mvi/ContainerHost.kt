package com.sun5066.core.mvi

import com.sun5066.core.mvi.annoation.MviDsl
import kotlinx.coroutines.Job

interface ContainerHost<STATE : Any, SIDE_EFFECT : Any> {

    val container: Container<STATE, SIDE_EFFECT>

    @MviDsl
    fun intent(
        transformer: suspend Syntax<STATE, SIDE_EFFECT>.() -> Unit
    ): Job = container.intent { Syntax(this).transformer() }

}