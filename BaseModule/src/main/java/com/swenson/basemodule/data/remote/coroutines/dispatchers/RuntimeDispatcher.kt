package com.swenson.basemodule.data.remote.coroutines.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class RuntimeDispatcher : BaseCoroutineDispatcher {
    override fun main(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun io(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}