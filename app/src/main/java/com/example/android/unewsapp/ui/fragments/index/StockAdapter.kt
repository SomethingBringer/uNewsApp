package com.example.android.unewsapp.ui.fragments.index

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.android.unewsapp.R
import com.example.android.unewsapp.core.RecyclerAdapter
import com.example.android.unewsapp.models.News
import com.example.android.unewsapp.models.StockItem
import kotlinx.android.synthetic.main.index_item.view.*
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*

class StockAdapter: RecyclerAdapter<StockItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<StockItem> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.index_item, parent, false)

        return object : BindingHolder<StockItem>(view) {
            override fun bind(model: StockItem, position: Int) {
                view.tvIndexName.text = model.symbol.capitalize(Locale.getDefault())
                view.tvIndexValue.text = model.todayValue.toString()
                view.tvIndexChange.text = model.changedValue.toString()
                view.tvIndexChange.backgroundTintList =
                    ColorStateList.valueOf(
                        Color.parseColor(
                            if (model.changedValue ?: 0f <= 0f) "#FFF40000"
                            else "#FF10F400")
                    )
            }
        }
    }
}