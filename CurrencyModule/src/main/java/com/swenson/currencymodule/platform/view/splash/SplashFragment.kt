package com.swenson.currencymodule.platform.view.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.swenson.currencymodule.R
import com.swenson.basemodule.base.platform.view.BaseFragment
import com.swenson.currencymodule.databinding.FragmentSplashBinding


class SplashFragment : BaseFragment<SplashViewModel, FragmentSplashBinding>() {

    override fun initialize() {
        mViewModel = ViewModelProviders.of(
            this,
            SplashViewModelFactory()
        ).get(SplashViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }


    private fun openCurrencyPage() {
        val action = SplashFragmentDirections.actionSplashFragmentToCurrencyFragment()
        navigate(findNavController(), action)
    }

    fun observe() {

        mViewModel.isReadyToRedirect.observe(requireActivity(), Observer {
            openCurrencyPage()
        })
    }

    override fun getLayoutId(): Int = R.layout.fragment_splash

    override fun onResume() {
        mViewModel.setTimeSplash()
        observe()
        super.onResume()
    }

}