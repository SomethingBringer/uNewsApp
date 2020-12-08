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
import com.example.android.unewsapp.utils.nullIfBlank
import com.example.android.unewsapp.utils.nullIfEmpty
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSearchViewModel @Inject constructor(
    private val api: RetrofitApi
) : ViewModel() {

    val newsLiveData = MutableLiveData<List<News>?>()
    val errorLiveData = MutableLiveData<ErrorEntity>()
    val stateLiveData = MutableLiveData<State>()
    val tagsLiveData = MutableLiveData<List<String>>()
    val selectedTags = mutableListOf<String>()

    var lastText = ""
    private var timerLeft = 0
    private var timerJob: Job? = null

    private var intervalStart: String = ""
    val start get() = intervalStart
    private var intervalEnd: String = ""
    val end get() = intervalEnd

    fun updateInterval(isStartUpdate: Boolean, value: String) {
        if (isStartUpdate)
            intervalStart = value
        else intervalEnd = value
    }

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
            val resource =
                api.searchNews(
                    query.nullIfBlank(),
                    selectedTags.nullIfEmpty() as List<String>?,
                    intervalStart.nullIfBlank(),
                    intervalEnd.nullIfBlank()
                )
            val error = resource.errorEntity
            if (resource.data != null) {
                newsLiveData.value = resource.data.data
            } else if(error!=null){
                errorLiveData.value = error
            }
            stateLiveData.value = SHOW
        }
    }

    fun getTags() = viewModelScope.launch {
        stateLiveData.value = LOADING
        val resource = api.getTags()
        val error = resource.errorEntity
        if (resource.data != null) {
            tagsLiveData.value = resource.data
        } else if (error !=null) {
            errorLiveData.value = error
        }
        stateLiveData.value = SHOW
    }

    sealed class State {
        object SHOW : State()
        object LOADING : State()
    }
}