package com.example.android.unewsapp.remote.api

import com.example.android.unewsapp.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("stable/stock/market/collection/sector")
    suspend fun getTodayIndexes(
        @Query("collectionName") collectionName: String,
        @Query("token") token: String
    ): Response<StockWrapper>
}