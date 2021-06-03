package com.swenson.currencymodule.platform.view.currency

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.swenson.basemodule.base.platform.view.BaseFragment
import com.swenson.basemodule.helpers.UtilityHelper
import com.swenson.basemodule.models.Currency
import com.swenson.currencymodule.R
import com.swenson.currencymodule.databinding.FragmentCurrencyCalculatorBinding
import kotlinx.android.synthetic.main.fragment_currency_calculator.*
import kotlinx.android.synthetic.main.toolbar.*

class CurrencyCalculatorFragment :
    BaseFragment<CurrencyViewModel, FragmentCurrencyCalculatorBinding>() {

    var mCurrency: Currency? = null

    var baseCurrency = UtilityHelper.getBaseCurrency()

    override fun getLayoutId(): Int = R.layout.fragment_currency_calculator

    override fun initialize() {
        mViewModel = ViewModelProviders.of(
            this,
            CurrencyViewModeFactory(
                requireContext()
            )
        ).get(CurrencyViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCurrency = arguments?.getParcelable<Currency>("currency")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_title.text = getText(R.string.currency_calculator)
        currency_txv.text = UtilityHelper.setCurrency(mCurrency!!.value!!)
        symbol_txv.text = mCurrency!!.symbol
        base_currency_edt.setText(UtilityHelper.setCurrency(baseCurrency.value!!))
        base_symbol_txv.text = baseCurrency.symbol

        mViewModel.calculateCurrency(mCurrency!!, baseCurrency.value!!)

        back_img.setOnClickListener {
            requireActivity().onBackPressed()
        }

        mViewModel.calculatedCurrency.observe(viewLifecycleOwner, Observer {
            currency_txv.text = UtilityHelper.setCurrency(it)
        })

        observe()
    }


    private fun observe() {
        base_currency_edt.requestFocus()
        showKeyboard(base_currency_edt)
        base_currency_edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text?.let {
                    if (!it.isEmpty())
                        mViewModel.calculateCurrency(mCurrency!!, it.toString().toDouble())
                }
            }

        })

    }
}