package com.swenson.basemodule.data.remote.coroutines.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface BaseCoroutineDispatcher {
    /**
     * Provides main thread coroutine dispatcher.
     * @return CoroutineDispatcher
     */
    fun main(): CoroutineDispatcher

    /**
     * Provides io thread coroutine dispatcher.
     * @return CoroutineDispatcher
     */
    fun io(): CoroutineDispatcher
}