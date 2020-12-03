package com.example.android.unewsapp.ui.fragments.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.unewsapp.remote.ErrorEntity
import com.example.android.unewsapp.remote.RetrofitApi
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.android.unewsapp.ui.fragments.widget.CustomSnackbar

class ConverterViewModel @Inject constructor(private val api: RetrofitApi) : ViewModel() {

    val valuesLiveData = MutableLiveData<Map<String, String>?>()
    val pairsLiveData = MutableLiveData<List<String>>()
    val currenciesLiveData = MutableLiveData<List<String>>()
    val errorLiveData = MutableLiveData<ErrorEntity>()

    var coef: Int = 1

    val currenciesList: List<String>
        get() {
            return currenciesLiveData.value ?: emptyList()
        }

    fun getValues(request: String) = viewModelScope.launch {
        val pairs =
            pairsLiveData.value?.filter { it.dropLast(3) == request } as? MutableList<String>
        val replace = pairsLiveData.value?.filter { it.drop(3) == request }
            ?.map { it.drop(3) + it.dropLast(3) }
        pairs?.let {
            if (replace != null) pairs.addAll(replace)
            val resource = api.getValues(pairs)
            val error = resource.errorEntity
            if (resource.data?.data != null) {
                valuesLiveData.value = resource.data.data
            } else if (error != null) {
                errorLiveData.value = error
            }
        }
    }

    fun getPairs() = viewModelScope.launch {
        val resource = api.getPairs()
        val error = resource.errorEntity
        if (resource.data != null) {
            pairsLiveData.value = resource.data.data
        } else if (error != null) {
            errorLiveData.value = error
        }
    }

    init {
        pairsLiveData.observeForever {
            if (!it.isNullOrEmpty()) {
                val currencyList = mutableListOf<String>()
                it.forEach { pair ->
                    currencyList.add(pair.dropLast(3))
                }
                currenciesLiveData.value = currencyList.distinct()
            }
        }

        errorLiveData.observeForever {
            if (it.text != null) {
                val errorText = it.text
                //CustomSnackbar.makeCustomSnackbar(this)
            }


        }
    }
}