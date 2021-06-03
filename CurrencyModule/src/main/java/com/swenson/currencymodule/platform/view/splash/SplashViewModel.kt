package com.swenson.currencymodule.platform.view.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.swenson.basemodule.base.platform.view.BaseViewModel

class SplashViewModel :
    BaseViewModel() {

    val isReadyToRedirect = MutableLiveData<Boolean>()

    fun setTimeSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            isReadyToRedirect.postValue(true)
        }, 3000)
    }
}