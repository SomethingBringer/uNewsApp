package com.example.android.unewsapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val id: Int,
    val title: String,
    val date: String,
    val summary: String,
    val fullText: String,
    val source: String,
    val keywords: List<String>
):Parcelable