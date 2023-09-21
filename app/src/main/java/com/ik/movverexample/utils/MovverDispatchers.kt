package com.ik.movverexample.utils

import kotlinx.coroutines.CoroutineDispatcher

interface MovverDispatchers {
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
}