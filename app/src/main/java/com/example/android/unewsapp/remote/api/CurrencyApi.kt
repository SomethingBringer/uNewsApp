package com.example.android.unewsapp.remote.api

import com.example.android.unewsapp.models.ModelWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("api/")
    suspend fun getValues(
        @Query("get") get: String = "rates",
        @Query("pairs") pairs: String,
        @Query("key") key: String
    ): Response<ModelWrapper<Map<String,String>>>

    @GET("api/")
    suspend fun getPairs(
        @Query("get") get: String = "currency_list",
        @Query("key") key: String
    ): Response<ModelWrapper<List<String>>>

}