package com.swenson.currencymodule.platform.view.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.swenson.basemodule.helpers.UtilityHelper
import com.swenson.basemodule.models.Currency
import com.swenson.currencymodule.R
import java.util.*


class CurrencyAdapter(
    private var currencyList: ArrayList<Currency>?,
    private var listener: CurrencyListener?

) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    interface CurrencyListener {
        fun onClickItem(mCurrency: Currency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)

        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currencyList?.get(position)
        item?.let {
            holder.symbol_txv?.text = item.symbol
            holder.currency_txv?.text = UtilityHelper.setCurrency(item.value!!)

            holder.currency_layout?.setOnClickListener {
                listener!!.onClickItem(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return currencyList!!.size
    }


    inner class ViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) {

        var currency_layout: View? = convertView.findViewById(R.id.currency_layout)
        var currency_txv: AppCompatTextView? = convertView.findViewById(R.id.currency_txv)
        var symbol_txv: AppCompatTextView? = convertView.findViewById(R.id.symbol_txv)

    }
}

