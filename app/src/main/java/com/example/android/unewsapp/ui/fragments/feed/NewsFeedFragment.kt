package com.example.android.unewsapp.ui.fragments.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.unewsapp.MyApplication
import com.example.android.unewsapp.R
import com.example.android.unewsapp.ui.fragments.feed.NewsFeedViewModel.State.LOADING
import com.example.android.unewsapp.ui.fragments.feed.NewsFeedViewModel.State.SHOW
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_news_feed.*
import javax.inject.Inject

class NewsFeedFragment : Fragment(), TabLayout.OnTabSelectedListener {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: NewsFeedViewModel
    lateinit var newsAdapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(NewsFeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_feed, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeLiveData()
        tabLayout.getTabAt(viewModel.selectedPosition)?.select()
        tabLayout.addOnTabSelectedListener(this)

        if(viewModel.newsLiveData.value==null) {
            viewModel.getNews(viewModel.selectedPosition)
        }
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
                is SHOW -> {
                    newsRecycler.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
                is LOADING -> {
                    newsRecycler.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab ?: return
        val pos = tab.position
        viewModel.getNews(pos)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}



}