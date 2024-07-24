package com.sun5066.core.mvi

fun <STATE : Any, SIDE_EFFECT : Any> ContainerHost<STATE, SIDE_EFFECT>.container(
    state: STATE,
): Container<STATE, SIDE_EFFECT> {
    return ContainerImpl(
        initialState = state,
    )
}