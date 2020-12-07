package com.example.android.unewsapp.utils

fun String.nullIfBlank(): String? = if (isBlank()) null else this

fun <T> Collection<T>.nullIfEmpty(): Collection<T>? = if (isEmpty()) null else this