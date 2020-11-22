package com.example.android.unewsapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class NewsWrapper(
    @SerializedName("rows") val data: List<News>
)

@Parcelize
data class News(
    val id: Int,
    val title: String,
    val date: String,
    val summary: String,
    @SerializedName("full_text") val fullText: String,
    val source: String,
    val keywords: String,
    val tag: String,
    val image: String
): Parcelable