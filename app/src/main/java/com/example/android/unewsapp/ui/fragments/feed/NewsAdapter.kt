package com.example.android.unewsapp.ui.fragments.feed

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.android.unewsapp.R
import com.example.android.unewsapp.core.RecyclerAdapter
import com.example.android.unewsapp.models.News
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*

class NewsAdapter(
    private val onNewsClick: (model: News) -> Unit
) : RecyclerAdapter<News>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<News> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val dateFormatter = DateFormat.getDateFormat(parent.context)

        return object : BindingHolder<News>(view) {

            override fun bind(model: News, position: Int) {
                view.title.text = model.title
                val sourceText = "Источник: ${model.source}"
                view.source.text = sourceText
                //val infoText = "${model.tag.capitalize()}: ${dateFormatter.format(Date.parse(model.date))}"
                val infoText = "${model.tag.capitalize()}"
                view.info.text = infoText
                Glide.with(parent.context).load(model.image).into(view.image)
                //view.image.visibility = View.GONE

                view.setOnClickListener {
                    onNewsClick.invoke(model)
                }
            }
        }
    }
}