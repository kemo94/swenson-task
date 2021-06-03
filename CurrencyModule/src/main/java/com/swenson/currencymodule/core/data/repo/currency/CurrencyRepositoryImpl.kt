package com.swenson.currencymodule.core.data.repo.currency

import com.swenson.basemodule.base.core.data.repo.BaseRepositoryImpl
import com.swenson.currencymodule.core.data.remote.currency.CurrencyRemoteDataSource

class CurrencyRepositoryImpl(
    private val remoteDataSource: CurrencyRemoteDataSource
) : BaseRepositoryImpl(remoteDataSource), CurrencyRepository {

    override suspend fun getLatestCurrency(accessToken: String, base: String) =
        remoteDataSource.getLatestCurrency(accessToken, base)

}