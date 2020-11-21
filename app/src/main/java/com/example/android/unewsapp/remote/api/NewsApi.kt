package com.example.android.unewsapp.remote.api

import com.example.android.unewsapp.models.ModelWrapper
import com.example.android.unewsapp.models.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("someUrl")
    suspend fun getNews(
        @Query("slug") slug: String
    ): Response<ModelWrapper<List<News>>>

    @GET("someUrl")
    suspend fun searchNews(
        @Query("slug") slug: String
    ): Response<ModelWrapper<List<News>>>
}