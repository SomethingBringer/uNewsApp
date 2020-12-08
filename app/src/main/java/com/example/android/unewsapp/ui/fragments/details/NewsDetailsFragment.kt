package com.example.android.unewsapp.ui.fragments.details

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.android.unewsapp.R
import com.example.android.unewsapp.models.News
import kotlinx.android.synthetic.main.fragment_news_details.*
import kotlinx.android.synthetic.main.item_news.view.*
import java.lang.IllegalArgumentException
import java.util.*

class NewsDetailsFragment : Fragment() {
    private var model: News? = null
    val dateFormatter by lazy {DateFormat.getDateFormat(requireContext())}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = arguments?.getParcelable(MODEL_KEY) ?: throw IllegalArgumentException()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model?.let {
            Glide.with(view.context).load(it.image).into(ivPhoto)
            tvSummary.text = it.fullText
            val infoText = "${it.tag.capitalize(Locale.getDefault())}: ${it.date.substring(0,10)}"
            val sourceText = "Источник: ${it.source}"
            tvInfo.text = infoText
            tvSource.text = sourceText
        }
    }

    companion object {
        const val MODEL_KEY = "MODEL_KEY"
    }
}