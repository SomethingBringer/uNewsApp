package com.example.android.unewsapp.remote

import com.example.android.unewsapp.BuildConfig
import com.example.android.unewsapp.core.NewsTag
import com.example.android.unewsapp.models.News
import com.example.android.unewsapp.models.NewsCount
import com.example.android.unewsapp.models.NewsWrapper
import com.example.android.unewsapp.remote.api.NewsApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitApi @Inject constructor(
    private val gson:Gson
) {
    private val URL = "http://62.113.118.217:8000/"
    private val news by lazy {  getRetrofit().create(NewsApi::class.java)}
    private fun getRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .apply {
                if (BuildConfig.DEBUG) {
                    val logger = HttpLoggingInterceptor()
                    logger.level = HttpLoggingInterceptor.Level.BODY
                    this.addNetworkInterceptor(logger)
                }
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(URL)
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
                NewsTag.ECONOMY -> news.getRussiaNews()
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


    suspend fun searchNews(slug: String): Resource<NewsWrapper> {
//        return responseWrapper {
//            news.getNews(slug)
//        }
        delay(5000);
        val list = mutableListOf<News>()
     /*   list.add(News(1,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(2,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(3,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(4,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(11,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(13,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(15,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(16,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(19,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(10,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(100,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(111,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(122,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
        list.add(News(133,"ЦСКА одержал третью подряд победу в чемпионате России","24.10.2020","summary","fulltext","РБК", listOf(slug)))
   */     return Resource.success(NewsWrapper(list))
    }

    private suspend fun <T> responseWrapper(block: suspend () -> Response<T>): Resource<T> {
        return safeApiCall(Dispatchers.IO, block)
    }

    companion object {
        private const val TIMEOUT = 1800L
    }
}