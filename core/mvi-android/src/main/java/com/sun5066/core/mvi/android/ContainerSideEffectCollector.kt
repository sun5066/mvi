package com.sun5066.core.mvi.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.sun5066.core.mvi.ContainerHost

@Composable
fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.collectContainerSideEffect(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend (SIDE_EFFECT) -> Unit
) {
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(minActiveState) {
            container.sideEffect.collect(action)
        }
    }
}