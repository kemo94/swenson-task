package com.swenson.currencymodule.core.domain.currency

import com.swenson.basemodule.base.core.domain.BaseUseCaseImpl
import com.swenson.basemodule.data.remote.coroutines.CoroutinesProviderImpl
import com.swenson.basemodule.data.remote.coroutines.Result
import com.swenson.basemodule.helpers.UtilityHelper
import com.swenson.currencymodule.core.data.repo.currency.CurrencyRepository

class CurrencyUseCaseImpl(
    private val repository: CurrencyRepository,
    private val coroutines: CoroutinesProviderImpl
) : BaseUseCaseImpl(repository, coroutines), CurrencyUseCase {

    override fun getLatestCurrency(
        accessToken: String,
        callback: (Result) -> Unit
    ) {
        var baseCurrency = UtilityHelper.getBaseCurrency()

        coroutines.request(
            { repository.getLatestCurrency(accessToken, baseCurrency.symbol) },
            { callback(it) })
    }

}