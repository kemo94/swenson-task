package com.swenson.basemodule.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class Currency(var symbol: String, var value: Double) : Parcelable