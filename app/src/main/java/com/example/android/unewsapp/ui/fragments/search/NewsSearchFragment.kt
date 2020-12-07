package com.example.android.unewsapp.ui.fragments.search

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.example.android.unewsapp.ui.fragments.details.NewsDetailsFragment
import com.example.android.unewsapp.ui.fragments.feed.NewsAdapter
import com.example.android.unewsapp.utils.toIsoDateString
import kotlinx.android.synthetic.main.fragment_news_feed.newsRecycler
import kotlinx.android.synthetic.main.fragment_news_feed.progressBar
import kotlinx.android.synthetic.main.fragment_news_search.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
//import sun.security.jgss.GSSUtil.login
import javax.inject.Inject


class NewsSearchFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: NewsSearchViewModel
    lateinit var newsAdapter: NewsAdapter

    private val tagPopupMenu by lazy {
        PopupMenu(requireContext(), btnSelectTags).apply {
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
        viewModel.getTags()
        initListeners()
        observeLiveData()

        if (viewModel.start.isNotBlank()) btnStartDate.text = viewModel.start
        if (viewModel.end.isNotBlank()) btnEndDate.text = viewModel.end
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter { model ->
            findNavController().navigate(
                R.id.newsDetailsFragment,
                Bundle().apply {
                    putParcelable(NewsDetailsFragment.MODEL_KEY, model)
                    putString("title", model.title)
                })
        }
        newsRecycler.apply {
            adapter = newsAdapter
        }
    }

    private fun initListeners() {
        btnSelectTags.setOnClickListener { tagPopupMenu.show() }

        ivCross.setOnClickListener { etSearch.text.clear() }

        btnStartDate.setOnClickListener { initDatePikingDialog(true) }

        btnEndDate.setOnClickListener { initDatePikingDialog(false) }

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
        viewModel.tagsLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                tagPopupMenu.menu.clear()
                it.forEachIndexed { index, item -> tagPopupMenu.menu.add(1, index, index, item) }
                tagPopupMenu.menu.setGroupCheckable(1, true, false)
            }
        }
    }

    private fun setDefaultMode() {
        ivCross.visibility = View.GONE
    }

    private fun setSearchMode() {
        ivCross.visibility = View.VISIBLE
    }

    private fun initTagMenuOnClickListener(item: MenuItem): Boolean {
        if (item.isChecked)
            viewModel.selectedTags.remove(item.title.toString())
        else
            viewModel.selectedTags.add(item.title.toString())
        item.isChecked = !item.isChecked
        viewModel.searchNews(etSearch.text.toString())
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
        return false
    }

    private fun initDatePikingDialog(isStart: Boolean) {
        val pickerDialog = DatePickerDialog(requireContext(),
            { _, year, month, dayOfMonth ->
                val pickedDate = LocalDateTime.of(year,month,dayOfMonth,0,0).toIsoDateString()
                viewModel.updateInterval(isStart,pickedDate)
                if (isStart)
                    btnStartDate.text = pickedDate
                else btnEndDate.text = pickedDate
            },
            LocalDateTime.now().atZone(ZoneId.systemDefault()).year,
            LocalDateTime.now().atZone(ZoneId.systemDefault()).monthValue - 1,
            LocalDateTime.now().atZone(ZoneId.systemDefault()).dayOfMonth
        )
        pickerDialog.show()
    }

}


