package com.example.android.unewsapp.ui.fragments.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.unewsapp.models.News
import com.example.android.unewsapp.remote.ErrorEntity
import com.example.android.unewsapp.remote.RetrofitApi
import com.example.android.unewsapp.ui.fragments.feed.NewsFeedViewModel
import com.example.android.unewsapp.ui.fragments.search.NewsSearchViewModel.State.LOADING
import com.example.android.unewsapp.ui.fragments.search.NewsSearchViewModel.State.SHOW
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSearchViewModel @Inject constructor(
    private val api: RetrofitApi
) : ViewModel() {


    val newsLiveData = MutableLiveData<List<News>>()
    val errorLiveData = MutableLiveData<ErrorEntity>()
    val stateLiveData = MutableLiveData<State>()


    fun searchNews(keyword: String) {
        val slug = keyword
        viewModelScope.launch {
            stateLiveData.value = NewsSearchViewModel.State.LOADING
            val response = api.searchNews(keyword)
            stateLiveData.value = NewsSearchViewModel.State.SHOW
            val data = response.data?.data
            val error = response.errorEntity
            data?.let { news ->
                newsLiveData.value = news
            }
            error?.let { errorEntity ->
                errorLiveData.value = errorEntity
            }
        }
    }

    sealed class State {
        object SHOW : State()
        object LOADING : State()
    }
}