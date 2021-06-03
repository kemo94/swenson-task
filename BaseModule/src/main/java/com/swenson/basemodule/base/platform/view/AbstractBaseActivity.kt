package com.swenson.basemodule.base.platform.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity


abstract class AbstractBaseActivity : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResId: Int


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        onBackPressed()
        return true
    }


    fun showMessage(message: String) {
        showLongToaster(this, message)
    }

    fun showKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun showLongToaster(context: Context?, message: String) {

        if (!TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun onFragmentAttached() {

    }

}
