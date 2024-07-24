package com.sun5066.mvi

import androidx.lifecycle.ViewModel
import com.sun5066.core.mvi.ContainerHost
import com.sun5066.core.mvi.container

class MainViewModel : ViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container = container(MainState())

    fun increment() = intent {
        val count = state.count + 1

        if (count > 5) {
            postSideEffect(MainSideEffect.Toast("Count exceeded 5!"))
        } else {
            reduce { state.copy(count = count) }
        }
    }
}

sealed interface MainSideEffect {
    data class Toast(val message: String) : MainSideEffect
}

data class MainState(
    val count: Int = 0
)