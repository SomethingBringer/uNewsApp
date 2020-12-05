package com.example.android.unewsapp.remote.api

import com.example.android.unewsapp.models.NewsWrapper
import com.example.android.unewsapp.models.NewsCount
import com.example.android.unewsapp.models.ModelWrapper
import com.example.android.unewsapp.models.News

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("api/russia")
    suspend fun getRussiaNews(): Response<NewsWrapper>

    @GET("api/sport")
    suspend fun getSportNews(): Response<NewsWrapper>

    @GET("api/art")
    suspend fun getArtNews(): Response<NewsWrapper>

    @GET("api/science")
    suspend fun getScienceNews(): Response<NewsWrapper>

    @GET("api/economy")
    suspend fun getEconomyNews(): Response<NewsWrapper>

    @GET("api/getallnews")
    suspend fun getAllNews(): Response<NewsWrapper>

    @GET("api/count")
    suspend fun getNewsCount(): Response<NewsCount>

    @GET("api/find")
    suspend fun searchNews(
        @Query("words") query: String?,
        @Query("tags") tags: List<String>?
    ): Response<NewsWrapper>

    @GET("api/gettags2")
    suspend fun getTags(): Response<List<String>>
}