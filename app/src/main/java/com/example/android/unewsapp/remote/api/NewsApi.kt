package com.example.android.unewsapp.remote.api

import com.example.android.unewsapp.models.NewsCount
import com.example.android.unewsapp.models.NewsWrapper
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

    @GET("someUrl")
    suspend fun searchNews(
        @Query("slug") slug: String
    ): Response<NewsWrapper>

    @GET("api/getallnews")
    suspend fun getAllNews(): Response<NewsWrapper>

    @GET("api/count")
    suspend fun getNewsCount(): Response<NewsCount>

}