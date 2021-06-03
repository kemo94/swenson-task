package com.swenson.basemodule.models

import com.google.gson.annotations.SerializedName


data class MessageResponse(
    @SerializedName(value = "msg", alternate = ["message"])
    val message: String
)