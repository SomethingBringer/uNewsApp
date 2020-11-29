package com.example.android.unewsapp.remote

import com.example.android.unewsapp.core.NewsTag
import com.example.android.unewsapp.models.ModelWrapper
import com.example.android.unewsapp.models.NewsCount
import com.example.android.unewsapp.models.NewsWrapper
import com.example.android.unewsapp.models.StockWrapper
import com.example.android.unewsapp.remote.api.NewsApi
import com.example.android.unewsapp.remote.api.CurrencyApi
import com.example.android.unewsapp.remote.api.StockApi
import com.example.android.unewsapp.utils.KeyStore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitApi @Inject constructor(
    private val gson:Gson
) {

    private val URL = "http://62.113.118.217:8000/"
    private val STOCK_TOKEN = "pk_ac91a73b395d4cf0beb315f91dd647ae"
    private val news by lazy {  getRetrofit(URL).create(NewsApi::class.java)}
    private val currency by lazy { getRetrofit("https://currate.ru/").create(CurrencyApi::class.java)}
    private val stock by lazy { getRetrofit("https://cloud.iexapis.com/").create(StockApi::class.java)}

    private fun getRetrofit(baseUrl: String): Retrofit {

        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .apply {
                if (true) {
                    val logger = HttpLoggingInterceptor()
                    logger.level = HttpLoggingInterceptor.Level.BODY
                    this.addNetworkInterceptor(logger)
                }
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    suspend fun getNewsWithTag(tag: NewsTag): Resource<NewsWrapper> {
        return responseWrapper {
            when(tag){
                NewsTag.RUSSIA -> news.getRussiaNews()
                NewsTag.SPORT -> news.getSportNews()
                NewsTag.ART -> news.getArtNews()
                NewsTag.SCIENCE -> news.getScienceNews()
                NewsTag.ECONOMY -> news.getEconomyNews()
            }
            //news.getNewsWithTag(tag)
        }
    }

    suspend fun getAllNews(): Resource<NewsWrapper> {
        return responseWrapper {
            news.getAllNews()
        }
    }

    suspend fun getNewsCount(): Resource<NewsCount>{
        return responseWrapper {
            news.getNewsCount()
        }
    }

    //Todo: Fill key in getValues()
    suspend fun getPairs(): Resource<ModelWrapper<List<String>>> {
        return responseWrapper {
            currency.getPairs(key = KeyStore.key)
        }
    }

    //Todo: Fill key in getValues()
    suspend fun getValues(pairs: List<String>): Resource<ModelWrapper<Map<String,String>>> {
        return responseWrapper {
            currency.getValues(pairs = pairs.joinToString(separator=","), key = KeyStore.key)
        }
    }

    suspend fun getStockIndexes(collectionName: String = "Technology"): Resource<StockWrapper>{
        return responseWrapper {
            stock.getTodayIndexes(collectionName, STOCK_TOKEN)
        }
    }

    private suspend fun <T> responseWrapper(block: suspend () -> Response<T>): Resource<T> {
        return safeApiCall(Dispatchers.IO, block)
    }

    companion object {
        private const val TIMEOUT = 1800L
    }
}