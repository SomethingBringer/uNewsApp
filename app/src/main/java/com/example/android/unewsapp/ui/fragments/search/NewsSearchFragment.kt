package com.example.android.unewsapp.ui.fragments.search

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
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
import javax.inject.Inject

class NewsSearchFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: NewsSearchViewModel
    lateinit var newsAdapter: NewsAdapter


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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        etSearch.doAfterTextChanged { text ->
            if (text.isNullOrBlank()) {
                setDefaultMode()
            } else {
                setSearchMode()
                viewModel.searchNews(text.toString())
            }
        }

        btnSelectTime.setOnClickListener {
            val menu = PopupMenu(context, it)
            menu.inflate(R.menu.time_selection_menu)
            menu.show()
        }

        btnSelectTags.setOnClickListener {
            val tagMenu = PopupMenu(context, it)
            tagMenu.inflate(R.menu.tag_selection_menu)
            tagMenu.menu.findItem(R.id.menuPopular).isEnabled = false
            tagMenu.menu.findItem(R.id.menuCountries).isEnabled = false
            /*
            tagMenu.setOnMenuItemClickListener {
                it.isChecked = !it.isChecked
                true
            }

             */

            tagMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                item.isChecked = !item.isChecked

                // Do other stuff

                // Keep the popup menu open
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
                item.actionView = View(context)
                item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                        return false
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                        return false
                    }
                })
                false
            })

            tagMenu.show()
        }
        ivCross.setOnClickListener { etSearch.text.clear() }
        observeLiveData()
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter { model ->
            findNavController().navigate(
                R.id.action_newsFeedFragment_to_newsDetailsFragment,
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
                }
                is NewsSearchViewModel.State.LOADING -> {
                    newsRecycler.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
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
}


