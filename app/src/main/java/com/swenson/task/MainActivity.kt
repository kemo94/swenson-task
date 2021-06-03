package com.swenson.task

import android.os.Bundle
import com.swenson.basemodule.base.platform.view.AbstractBaseActivity

class MainActivity : AbstractBaseActivity() {

    override val layoutResId: Int get() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}