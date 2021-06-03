package com.swenson.basemodule.base.core.domain

import com.swenson.basemodule.base.core.data.repo.BaseRepository
import com.swenson.basemodule.data.remote.coroutines.CoroutinesProviderImpl

open class BaseUseCaseImpl(
    private val baseRepository: BaseRepository,
    private val coroutines: CoroutinesProviderImpl
) : BaseUseCase {

}