package com.swenson.basemodule.base.platform.view

import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar

interface BaseView {
    fun hasToolbar(): Boolean
    fun isBackEnabled(): Boolean
    fun screenToolbar(): Toolbar?
    fun toolbarTitle(): String
    fun toolbarTitleView(): AppCompatTextView?
    fun startWork()
}