package com.example.android.unewsapp.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.unewsapp.R
import com.example.android.unewsapp.models.News
import kotlinx.android.synthetic.main.fragment_news_details.*
import java.lang.IllegalArgumentException

class NewsDetailsFragment : Fragment() {

    private var model: News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = arguments?.getParcelable("KEY") ?: throw IllegalArgumentException()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivPhoto.visibility = View.GONE
        model?.let {
            tvSummary.text = it.fullText
            val sourceText = "Источник: ${it.source}"
            val infoText = "${it.keywords[0]}: ${it.date}"
            tvInfo.text = infoText
            tvSource.text = sourceText
        }
    }


}