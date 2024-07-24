package com.sun5066.core.mvi

import com.sun5066.core.mvi.annoation.MviDsl

@MviDsl
data class IntentContext<STATE : Any>(val state: STATE)
