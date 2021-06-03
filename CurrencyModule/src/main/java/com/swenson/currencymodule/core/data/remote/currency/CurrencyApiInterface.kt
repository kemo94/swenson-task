package com.swenson.currencymodule.core.data.remote.currency

import com.swenson.basemodule.base.core.data.remote.BaseApiInterface
import com.swenson.basemodule.models.ApiResponse
import retrofit2.http.*

interface CurrencyApiInterface : BaseApiInterface {

    @GET("latest")
    suspend fun getLatestCurrency(
        @Query("access_key") access_key: String,
        @Query("base") base: String
    ): ApiResponse<HashMap<String,Double>>

}