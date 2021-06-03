package com.swenson.currencymodule.platform.view.currency

import androidx.lifecycle.MutableLiveData
import com.swenson.basemodule.base.platform.view.BaseViewModel
import com.swenson.basemodule.data.remote.coroutines.Failure
import com.swenson.basemodule.data.remote.coroutines.Success
import com.swenson.basemodule.models.*
import com.swenson.currencymodule.core.domain.currency.CurrencyUseCase

class CurrencyViewModel(
    private val currencyUseCase: CurrencyUseCase
) : BaseViewModel() {

    var currenciesList = MutableLiveData<ArrayList<Currency>>()
    var calculatedCurrency = MutableLiveData<Double>()

    fun calculateCurrency(selectedCurrency: Currency, amount: Double) {
        calculatedCurrency.postValue(selectedCurrency.value!! * amount)
    }

    fun getLatestCurrency(
        accessToken: String
    ) {
        loading.postValue(true)
        currencyUseCase.getLatestCurrency(accessToken) {
            when (it) {
                is Success<*> -> {
                    val currencyResponse = it.result as ApiResponse<HashMap<String, Double>>
                    var currencyArray = ArrayList<Currency>();
                    for ((k, v) in currencyResponse.rates) {
                        currencyArray.add(Currency(k, v))
                    }
                    currenciesList.postValue(currencyArray)
                    loading.postValue(false)
                }
                is Failure<*> -> {
                    error.value = it.error
                    loading.postValue(false)
                }
            }
        }
    }


}