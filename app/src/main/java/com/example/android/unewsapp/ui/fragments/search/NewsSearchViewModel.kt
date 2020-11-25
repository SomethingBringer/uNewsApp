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

    private var allNews: List<News>? = null

    val newsLiveData = MutableLiveData<List<News>>()
    val errorLiveData = MutableLiveData<ErrorEntity>()
    val stateLiveData = MutableLiveData<State>()

    //Todo: Give a kick to backender for correct words&tag endpoint with queries
    fun cacheAllNews(){
        viewModelScope.launch {
            stateLiveData.value = NewsSearchViewModel.State.LOADING
            allNews ?: run { allNews = loadNews() }
            stateLiveData.value = NewsSearchViewModel.State.SHOW
        }
    }

    suspend fun loadNews(): List<News> {
        val response = api.getAllNews()
        val data = response.data?.data
        val error = response.errorEntity
        data?.let { news ->
            return news
        }
        error?.let { errorEntity ->
            errorLiveData.value = errorEntity
        }
        return emptyList()
    }


    fun searchNews(keyword: String) {
        viewModelScope.launch {
            stateLiveData.value = NewsSearchViewModel.State.LOADING
            val filteredNews = allNews?.filter { it.fullText.contains(keyword.toRegex()) && it.title.contains(keyword.toRegex()) } ?: emptyList()
            stateLiveData.value = NewsSearchViewModel.State.SHOW
            newsLiveData.value = filteredNews
        }
    }

    sealed class State {
        object SHOW : State()
        object LOADING : State()
    }
}