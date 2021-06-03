package com.swenson.currencymodule.core.domain.currency

import com.swenson.basemodule.base.core.domain.BaseUseCase
import com.swenson.basemodule.data.remote.coroutines.Result

interface CurrencyUseCase : BaseUseCase {

    fun getLatestCurrency(
        accessToken: String,
        callback: ((Result) -> Unit)
    )

}