package com.swenson.currencymodule.platform.view.currency

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.swenson.basemodule.base.platform.view.BaseFragment
import com.swenson.basemodule.helpers.UtilityHelper
import com.swenson.basemodule.models.Currency
import com.swenson.currencymodule.R
import com.swenson.currencymodule.databinding.FragmentCurrencyBinding
import kotlinx.android.synthetic.main.fragment_currency.*
import kotlinx.android.synthetic.main.toolbar.*

class CurrencyFragment :
    BaseFragment<CurrencyViewModel, FragmentCurrencyBinding>(), CurrencyAdapter.CurrencyListener {


    override fun getLayoutId(): Int = R.layout.fragment_currency

    override fun initialize() {
        mViewModel = ViewModelProviders.of(
            this,
            CurrencyViewModeFactory(
                requireContext()
            )
        ).get(CurrencyViewModel::class.java)

    }

    var currencyadapter: CurrencyAdapter? = null
    private var currencyArray: ArrayList<Currency> = ArrayList()
    private var originalCurrencyArray: ArrayList<Currency> = ArrayList()
    var isLoading = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_title.text = UtilityHelper.getBaseCurrency().symbol

        mViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it)
                showLoadingDialog()
            else
                dismissLoadingDialog()
        })


        mViewModel.currenciesList.observe(viewLifecycleOwner, Observer {
            originalCurrencyArray.clear()
            originalCurrencyArray.addAll(it)
            fetchData(it)
        })
        setupRecycler()

        observe()
    }

    private fun setupRecycler() {
        val linearLayoutManager = LinearLayoutManager(context)
        currency_recycler.layoutManager = linearLayoutManager
        currency_recycler.setHasFixedSize(true)
        currencyadapter =
            CurrencyAdapter(
                currencyArray,
                this
            )
        currency_recycler.adapter = currencyadapter
    }

    private fun fetchData(it: List<Currency>) {
        if (it != null) {
            currencyArray.addAll(it)
            currencyadapter!!.notifyDataSetChanged()
            isLoading = false
            handleView()
        }
    }

    private fun getLatestCurrency() {
        isLoading = true
        mViewModel.getLatestCurrency(getAccessToken())
    }

    fun handleView() {
        if (currencyArray?.size != 0) {
            currency_recycler.visibility = View.VISIBLE
            empty_view.visibility = View.GONE
        } else {
            currency_recycler.visibility = View.GONE
            empty_view.visibility = View.VISIBLE
        }
    }


    private fun observe() {
        search_currency_edt.requestFocus()
        showKeyboard(search_currency_edt)
        search_currency_edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text?.let {

                    resetData()
                    var currencyArr = originalCurrencyArray.filter { currency ->
                        currency.symbol.toLowerCase().contains(it.toString().toLowerCase())
                    }

                    fetchData(currencyArr)

                }
            }

        })

    }

    private fun resetData() {
        currencyArray.clear()
        currencyadapter?.notifyDataSetChanged()
        isLoading = false
    }

    override fun onClickItem(mCurrency: Currency) {
        val bundle = Bundle()
        bundle.putParcelable(
            "currency",
            mCurrency
        )
        navigateWithActionId(
            findNavController(),
            R.id.action_currencyFragment_to_currencyCalculatorFragment,
            bundle
        )

    }

    // in case currency changed from database we need to load them again
    override fun onResume() {
        resetData()
        getLatestCurrency()
        search_currency_edt.setText("")
        super.onResume()
    }

}
