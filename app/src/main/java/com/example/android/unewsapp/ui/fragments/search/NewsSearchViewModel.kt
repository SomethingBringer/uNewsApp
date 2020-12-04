package com.example.android.unewsapp.ui.fragments.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.unewsapp.models.News
import com.example.android.unewsapp.remote.ErrorEntity
import com.example.android.unewsapp.remote.RetrofitApi
import com.example.android.unewsapp.ui.fragments.feed.NewsFeedViewModel
import com.example.android.unewsapp.ui.fragments.search.NewsSearchViewModel.State.LOADING
import com.example.android.unewsapp.ui.fragments.search.NewsSearchViewModel.State.SHOW
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSearchViewModel @Inject constructor(
    private val api: RetrofitApi
) : ViewModel() {

    val newsLiveData = MutableLiveData<List<News>>()
    val errorLiveData = MutableLiveData<ErrorEntity>()
    val stateLiveData = MutableLiveData<State>()

    var lastText = ""
    private var timerLeft = 0
    private var timerJob: Job? = null

    fun searchNews(query: String) {
        timerJob?.cancel()
        timerLeft = 5

        timerJob = viewModelScope.launch {
            while (timerLeft > 0) {
                delay(200)
                timerLeft--
                Log.d("timer", timerLeft.toString())
            }
            stateLiveData.value = LOADING
            val resource = api.searchNews(query)
            resource.data?.data.let {
                newsLiveData.value = it
            }
            stateLiveData.value = SHOW
        }
    }

    sealed class State {
        object SHOW : State()
        object LOADING : State()
    }
}