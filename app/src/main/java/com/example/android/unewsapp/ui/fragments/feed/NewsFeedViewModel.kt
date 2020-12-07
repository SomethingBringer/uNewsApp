package com.example.android.unewsapp.ui.fragments.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.unewsapp.core.NewsTag
import com.example.android.unewsapp.models.News
import com.example.android.unewsapp.remote.ErrorEntity
import com.example.android.unewsapp.remote.RetrofitApi
import com.example.android.unewsapp.ui.fragments.feed.NewsFeedViewModel.State.LOADING
import com.example.android.unewsapp.ui.fragments.feed.NewsFeedViewModel.State.SHOW
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val api: RetrofitApi
) : ViewModel() {

    private val tags = listOf(NewsTag.RUSSIA,NewsTag.SPORT,NewsTag.SCIENCE,NewsTag.ART,NewsTag.ECONOMY)
    val newsLiveData = MutableLiveData<List<News>>()
    val errorLiveData = MutableLiveData<ErrorEntity>()
    val stateLiveData = MutableLiveData<State>()

    private var mSelectedPosition = 0
    val selectedPosition get() = mSelectedPosition


    fun getNews(position: Int) {
        mSelectedPosition=position
        val tag = tags[position]
        viewModelScope.launch {
            stateLiveData.value = LOADING
            val response = api.getNewsWithTag(tag)
            val data = response.data?.data
            val error = response.errorEntity
            data?.let { news ->
                newsLiveData.value = news
            }
            error?.let { errorEntity ->
                errorLiveData.value = errorEntity
            }
            stateLiveData.value = SHOW
        }
    }


    sealed class State {
        object SHOW: State()
        object LOADING: State()
    }

}