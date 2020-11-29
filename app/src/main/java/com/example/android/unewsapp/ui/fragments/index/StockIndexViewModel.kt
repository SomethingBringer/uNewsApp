package com.example.android.unewsapp.ui.fragments.index

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.unewsapp.models.StockItem
import com.example.android.unewsapp.remote.RetrofitApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StockIndexViewModel @Inject constructor(
    private val api: RetrofitApi
): ViewModel() {
    val stockData = MutableLiveData<List<StockItem>>()

    fun getStockIndexes(){
        viewModelScope.launch{
            //val response = api.getStockIndexes()
            //val error = response.errorEntity
            /*response.data?.let { indexes ->
                stockData.postValue(indexes.data)
            } ?: */
            stockData.postValue(generateFakeStock())
//            error?.let { errorEntity ->
//                //errorLiveData.value = errorEntity
//            }
        }
    }

    fun generateFakeStock() = listOf(
        StockItem(
            symbol = "223310-KP",
            changedValue = 0f,
            todayValue = 3320f
        ),
        StockItem(
            symbol = "LGLOF",
            changedValue = -0.032f,
            todayValue = 0.303f
        ),
        StockItem(
            symbol = "SRNA",
            changedValue = -0.007f,
            todayValue = 0.06f
        ),
        StockItem(
            symbol = "CDLX",
            changedValue = 3.79f,
            todayValue = 0.67f
        ),
        StockItem(
            symbol = "OLOF",
            changedValue = 1f,
            todayValue = 3320f
        ),
        StockItem(
            symbol = "CHARS",
            changedValue = -0.032f,
            todayValue = 0.303f
        ),
        StockItem(
            symbol = "OPLLS",
            changedValue = -0.1f,
            todayValue = 0.06f
        ),
        StockItem(
            symbol = "FREWS",
            changedValue = 7.79f,
            todayValue = 1.67f
        ),
        StockItem(
            symbol = "APPL",
            changedValue = 213f,
            todayValue = 12003f
        ),
        StockItem(
            symbol = "LLLOQ",
            changedValue = -0.02f,
            todayValue = 0.403f
        ),
        StockItem(
            symbol = "SRNE",
            changedValue = 0.003f,
            todayValue = 0.17f
        ),
        StockItem(
            symbol = "CDLPS",
            changedValue = -3.79f,
            todayValue = 0.67f
        ),
    ).shuffled()
}