package com.example.android.unewsapp.models

import com.google.gson.annotations.SerializedName

data class NewsCount (
    @SerializedName("rows") val rows: List<NewsCountRow>
)

data class NewsCountRow(
    @SerializedName("count") val count: Int
)