package com.swenson.currencymodule.core.data.remote.currency

import com.swenson.basemodule.base.core.data.remote.BaseRemoteDataSource
import com.swenson.basemodule.models.ApiResponse

interface CurrencyRemoteDataSource : BaseRemoteDataSource {

    suspend fun getLatestCurrency(
        accessToken: String,
        base: String
    ): ApiResponse<HashMap<String, Double>>

}