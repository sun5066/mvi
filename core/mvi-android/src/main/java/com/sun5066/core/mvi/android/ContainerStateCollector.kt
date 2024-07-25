package com.sun5066.core.mvi.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.sun5066.core.mvi.ContainerHost
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectAsContainerStateWithLifecycle(
    initialValue: STATE = container.stateFlow.value,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    coroutineContext: CoroutineContext = EmptyCoroutineContext
): State<STATE> {
    val lifecycle = lifecycleOwner.lifecycle

    return produceState(initialValue, container.stateFlow, lifecycle, minActiveState, coroutineContext) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            if (coroutineContext == EmptyCoroutineContext) {
                container.stateFlow.collect { this@produceState.value = it }
            } else withContext(coroutineContext) {
                container.stateFlow.collect { this@produceState.value = it }
            }
        }
    }
}