package com.swenson.basemodule.base.core.data.repo

import com.swenson.basemodule.base.core.data.remote.BaseRemoteDataSource


open class BaseRepositoryImpl(
    private val baseRemoteDataSource: BaseRemoteDataSource
) : BaseRepository {

}