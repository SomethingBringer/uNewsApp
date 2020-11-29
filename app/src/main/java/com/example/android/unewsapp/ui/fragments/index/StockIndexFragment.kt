package com.example.android.unewsapp.ui.fragments.index

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.unewsapp.MyApplication
import com.example.android.unewsapp.R
import com.example.android.unewsapp.remote.RetrofitApi
import com.example.android.unewsapp.remote.api.NewsApi
import com.example.android.unewsapp.ui.fragments.converter.ConverterViewModel
import com.example.android.unewsapp.ui.fragments.feed.NewsAdapter
import kotlinx.android.synthetic.main.stock_index_fragment.*
import javax.inject.Inject

class StockIndexFragment : Fragment(R.layout.stock_index_fragment) {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    private lateinit var viewModel: StockIndexViewModel
    private val stockAdapter: StockAdapter by lazy { StockAdapter().apply { rvStockInfo.adapter = this } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(StockIndexViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvStockInfo.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getStockIndexes()
        initObservers()
    }

    private fun initObservers(){
        viewModel.stockData.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()) stockAdapter.submit(it)
        })
    }
}