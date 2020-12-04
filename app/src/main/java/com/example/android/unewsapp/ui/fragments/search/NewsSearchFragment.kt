package com.example.android.unewsapp.ui.fragments.search

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.Menu.NONE
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.core.view.forEach
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.unewsapp.MyApplication
import com.example.android.unewsapp.R
import com.example.android.unewsapp.ui.fragments.feed.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news_feed.newsRecycler
import kotlinx.android.synthetic.main.fragment_news_feed.progressBar
import kotlinx.android.synthetic.main.fragment_news_search.*
import java.util.*
//import sun.security.jgss.GSSUtil.login
import javax.inject.Inject


class NewsSearchFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: NewsSearchViewModel
    lateinit var newsAdapter: NewsAdapter
    val timePopupMenu by lazy {
        PopupMenu(requireContext(), btnSelectTime).apply {
            inflate(R.menu.time_selection_menu)
        }
    }
    val tagPopupMenu by lazy {
        PopupMenu(requireContext(), btnSelectTags).apply {
            inflate(R.menu.tag_selection_menu)
            setOnMenuItemClickListener { item -> initTagMenuOnClickListener(item) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(NewsSearchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_search, container, false)
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        etSearch.doAfterTextChanged { text ->
            if (text.isNullOrBlank()) {
                setDefaultMode()
            } else {
                setSearchMode()
                val s = text.toString()
                if (viewModel.lastText != s && s.length >= 3) {
                    viewModel.searchNews(text.toString())
                }
                viewModel.lastText = s
            }
        }

        btnSelectTime.setOnClickListener { timePopupMenu.show() }
        btnSelectTags.setOnClickListener { tagPopupMenu.show() }
        ivCross.setOnClickListener { etSearch.text.clear() }
        observeLiveData()
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter { model ->
            findNavController().navigate(
                R.id.newsDetailsFragment,
                Bundle().apply {
                    putParcelable("KEY", model)
                    putString("title", model.title)
                })
        }
        newsRecycler.apply {
            adapter = newsAdapter
        }
    }

    private fun observeLiveData() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { newsList ->
            if (!newsList.isNullOrEmpty()) {
                newsAdapter.submit(newsList)
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {

        }
        viewModel.stateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NewsSearchViewModel.State.SHOW -> {
                    newsRecycler.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    etSearch.isEnabled = true
                }
                is NewsSearchViewModel.State.LOADING -> {
                    newsRecycler.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    etSearch.isEnabled = false
                }
            }
        }
    }

    private fun setDefaultMode() {
        ivCross.visibility = View.GONE
    }

    private fun setSearchMode() {
        ivCross.visibility = View.VISIBLE
    }

    private fun initTagMenuOnClickListener(item: MenuItem): Boolean{
        tagPopupMenu.menu.forEach {
            it.isChecked = false
        }
        item.isChecked = !item.isChecked
        viewModel.searchNews(item.title.toString().toLowerCase(Locale.getDefault()))
        return false
    }

}


