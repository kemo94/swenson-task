package com.swenson.basemodule.base.platform.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.swenson.basemodule.R
import com.swenson.basemodule.models.MessageResponse
import com.swenson.basemodule.utility.*
import com.swenson.basemodule.utility.ToastHelper.showLongToaster
import retrofit2.HttpException
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment<P : BaseViewModel, V : ViewDataBinding> : Fragment() {


    var mActivity: AbstractBaseActivity? = null


    private var mRootView: View? = null
    lateinit var mViewDataBinding: V
    lateinit var mViewModel: P
    var progressDialog: Dialog? = null
    var locationType = 0

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initialize()

    fun getAccessToken(): String {
        if (activity != null)
            return activity!!.getString(R.string.access_token)

        return ""
    }

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
                    onApiError(it.code(), getErrorMsg(it))
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

    private fun getErrorMsg(it: Any?): String {
        if (it is HttpException) {
            return try {
                val messageResponse =
                    Gson().fromJson<MessageResponse>(
                        it.response()?.errorBody()?.charStream(),
                        MessageResponse::class.java
                    )
                messageResponse.message
            } catch (e: Exception) {
                return ""
            }
        }
        return ""
    }

    fun getBaseActivity(): AbstractBaseActivity? {
        return mActivity
    }

    // data not send correctly
    val BAD_REQUEST = 400

    open fun onApiError(code: Int, responseMessage: String) {
        showLongToaster(context, responseMessage)
        when (code) {
            BAD_REQUEST -> {
                Log.d("BaseFragment", "BAD_REQUEST")

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

    fun showToast(msg: String) {
        if (msg != null)
            ToastHelper.showLongToaster(requireActivity(), msg)
    }

    fun navigateWithActionId(navController: NavController, @IdRes resId: Int, args: Bundle?) {
        with(navController) {
            currentDestination?.getAction(resId)
                ?.let { navigate(resId, args, getLeftAndRightAnimationOptions()) }
        }
    }


    fun navigate(
        navController: NavController,
        action: NavDirections
    ) = with(navController) {
        currentDestination?.getAction(action.actionId)
            ?.let { navigate(action, getLeftAndRightAnimationOptions()) }
    }


    private fun getLeftAndRightAnimationOptions(): NavOptions? {

        return NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
    }
}