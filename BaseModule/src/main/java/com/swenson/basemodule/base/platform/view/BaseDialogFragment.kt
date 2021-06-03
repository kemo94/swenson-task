package com.swenson.basemodule.base.platform.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.swenson.basemodule.R
import com.swenson.basemodule.models.MessageResponse
import com.swenson.basemodule.utility.ToastHelper.showLongToaster
import retrofit2.HttpException
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseDialogFragment<P : BaseViewModel, V : ViewDataBinding> : DialogFragment() {


    var mActivity: AbstractBaseActivity? = null

    private var mRootView: View? = null
    lateinit var mViewDataBinding: V
    lateinit var mViewModel: P
    var progressDialog: Dialog? = null


    // data not send correctly
    val BAD_REQUEST = 400


    // data not send correctly
    val UNAUTHORIZE = 401


    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initialize()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AbstractBaseActivity) {
            val activity = context
            this.mActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding =
            DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = mViewDataBinding.root

        mViewDataBinding.lifecycleOwner = this

        return mRootView
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()

        mViewModel.message.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                mActivity?.showMessage(it)
                mViewModel.message.value = ""
            }
        })

        mViewModel.error.observe(viewLifecycleOwner, Observer {
            try {
                if (it is HttpException) {
                    var messageResponse =
                        Gson().fromJson<MessageResponse>(
                            it.response()?.errorBody()?.charStream(),
                            MessageResponse::class.java
                        )
                    onApiError(it.code(), messageResponse.message)
                }

                if (it is IOException) {
                    showLongToaster(context, getString(R.string.internet_connection_error))
                }
            } catch (e: Exception) {
                Log.e("error", e.toString())
            }
        }
        )

    }


    fun hideKeyboard() {
        if (mActivity != null) {
            mActivity!!.hideKeyboard()
        }
    }

    fun hideKeyboard(editText: EditText) {
        mActivity?.hideKeyboard(editText)
    }

    fun showKeyboard(editText: EditText) {
        mActivity?.showKeyboard(editText)
    }

    open fun onApiError(code: Int, responseMessage: String) {
        showLongToaster(context, responseMessage)
        when (code) {
            BAD_REQUEST -> {
                //  handleBadRequest()
                Log.d("BaseFragment", "BAD_REQUEST")

            }
            UNAUTHORIZE -> {
                Log.d("BaseFragment", responseMessage)
                Log.d("BaseFragment", "UNAUTHORIZE")
            }


            else -> {
                showLongToaster(context, responseMessage)
            }
        }
    }


    fun showLoadingDialog() {
        dismissLoadingDialog()
        context?.let {
            progressDialog = Dialog(it)
            progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog!!.setContentView(R.layout.dialog_progress)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }
    }

    fun dismissLoadingDialog() {
        progressDialog?.let {
            if (it.isShowing)
                progressDialog!!.dismiss()
        }
    }

}
