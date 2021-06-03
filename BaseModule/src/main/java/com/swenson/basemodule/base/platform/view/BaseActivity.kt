package com.swenson.basemodule.base.platform.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.swenson.basemodule.utility.NetworkUtils


abstract class BaseActivity<P : BaseViewModel, V : ViewDataBinding> : AppCompatActivity(),
    BaseView {

    lateinit var viewModel: P

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        if (hasToolbar()) {
            setSupportActionBar(screenToolbar())
            toolbarTitleView()?.text = toolbarTitle()
        }

        if (isBackEnabled()) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        viewModel.message.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                showLongToaster(this, it)
                viewModel.message.value = ""
            }
        })

        // The window will not be resized when virtual keyboard is shown
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    abstract fun initialize()

    @LayoutRes
    abstract fun getLayoutId(): Int


    override fun hasToolbar(): Boolean = false

    override fun isBackEnabled(): Boolean = false


    override fun screenToolbar(): Toolbar? = null


    abstract fun snackBarParentLayout(): View


    override fun toolbarTitle(): String = ""

    override fun toolbarTitleView(): AppCompatTextView? = null
    override fun startWork() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        onBackPressed()
        return true
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        startWork()
    }


    fun showLongToaster(context: Context?, message: String) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}
