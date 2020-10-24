package com.example.android.unewsapp.ui.fragments.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.unewsapp.R
import com.example.android.unewsapp.core.RecyclerAdapter
import com.example.android.unewsapp.models.News
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(
    private val onNewsClick: (model: News) -> Unit
) : RecyclerAdapter<News>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<News> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)

        return object : BindingHolder<News>(view) {
            override fun bind(model: News, position: Int) {
                view.title.text = model.title
                val sourceText = "Источник: ${model.source}"
                view.source.text = sourceText
                val category = model.keywords[0]
                val infoText = "$category: ${model.date}"
                view.info.text = infoText
                view.image.setImageResource(R.drawable.ic_launcher_background)
                //view.image.visibility = View.GONE

                view.setOnClickListener {
                    onNewsClick.invoke(model)
                }
            }
        }
    }
}