package com.swenson.currencymodule.core.data.remote.currency

import com.swenson.basemodule.base.core.data.remote.BaseRemoteDataSourceImpl

class CurrencyRemoteDataSourceImpl(private val currencyApiInterface: CurrencyApiInterface) :
    BaseRemoteDataSourceImpl(currencyApiInterface), CurrencyRemoteDataSource {

    override suspend fun getLatestCurrency(accessToken: String, base: String) =
        currencyApiInterface.getLatestCurrency(accessToken, base)

}