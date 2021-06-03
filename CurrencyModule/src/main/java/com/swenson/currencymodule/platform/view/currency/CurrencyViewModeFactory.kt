package com.swenson.currencymodule.platform.view.currency

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swenson.basemodule.data.helpers.network.RetrofitFactory
import com.swenson.basemodule.data.remote.coroutines.CoroutinesProviderImpl
import com.swenson.basemodule.data.remote.coroutines.dispatchers.RuntimeDispatcher
import com.swenson.currencymodule.core.data.remote.currency.CurrencyRemoteDataSourceImpl
import com.swenson.currencymodule.core.data.repo.currency.CurrencyRepositoryImpl
import com.swenson.currencymodule.core.domain.currency.CurrencyUseCaseImpl

class CurrencyViewModeFactory(
    private val context: Context
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val currencyUseCaseImpl = CurrencyUseCaseImpl(
            CurrencyRepositoryImpl(
                CurrencyRemoteDataSourceImpl(RetrofitFactory.getService(context))
            ), CoroutinesProviderImpl(RuntimeDispatcher())
        )

        return CurrencyViewModel(
            currencyUseCaseImpl
        ) as T
    }
}