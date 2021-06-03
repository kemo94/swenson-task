package com.swenson.currencymodule.core.data.repo.currency

import com.swenson.basemodule.base.core.data.repo.BaseRepository
import com.swenson.basemodule.models.ApiResponse

interface CurrencyRepository : BaseRepository {

    suspend fun getLatestCurrency( accessToken : String,baseCurrency : String)
            : ApiResponse<HashMap<String,Double>>


}