package com.swenson.basemodule.base.platform.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val message = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val shimmerLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Any>()
}