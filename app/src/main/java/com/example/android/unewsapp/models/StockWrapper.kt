package com.example.android.unewsapp.models

import com.google.gson.annotations.SerializedName

data class StockWrapper(
    val data: List<StockItem>
)

data class StockItem(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("latestPrice") val todayValue: Float?,
    @SerializedName("extendedChange") val changedValue: Float?,
)