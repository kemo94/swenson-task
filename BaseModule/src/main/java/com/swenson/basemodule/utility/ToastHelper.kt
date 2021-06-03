package com.swenson.basemodule.utility

import android.content.Context
import android.text.TextUtils
import android.widget.Toast


object ToastHelper {


    fun showLongToaster(context: Context?, message: String) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showShortToaster(context: Context?, message: String) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}