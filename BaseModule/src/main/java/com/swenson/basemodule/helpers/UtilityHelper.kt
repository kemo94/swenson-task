package com.swenson.basemodule.helpers

import com.swenson.basemodule.models.Currency
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


object UtilityHelper {

    private fun configDecimalFormat(): DecimalFormat {
        val symbols = DecimalFormatSymbols(Locale.US)
        val format = DecimalFormat("###,###,###.##", symbols)
        format.roundingMode = RoundingMode.FLOOR
        return format
    }

    fun setCurrency(currency: Double): String {
        return configDecimalFormat().format(((currency)))
    }

    fun getBaseCurrency(): Currency {
        return Currency("EUR", 1.0)
    }

}