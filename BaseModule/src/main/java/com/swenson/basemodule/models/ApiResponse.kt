package com.swenson.basemodule.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("base")
    @Expose
    var base: String,
    @SerializedName("success")
    @Expose
    var success: Boolean,
    @SerializedName("rates")
    @Expose
    var rates: T
)