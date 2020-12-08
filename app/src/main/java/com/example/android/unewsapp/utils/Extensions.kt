package com.example.android.unewsapp.utils

import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

/** Получение null если строка пуста */
fun String.nullIfBlank(): String? = if (isBlank()) null else this

/** Получение null если коллекция пуста */
fun <T> Collection<T>.nullIfEmpty(): Collection<T>? = if (isEmpty()) null else this

/** Получение строковой даты в формате ISO  */
fun TemporalAccessor.toIsoDateString() = toFormat("yyyy-MM-dd")

/** Получение форматированной строки по шаблону из класса даты и/или времени */
fun TemporalAccessor.toFormat(fmt: String): String {
    val formatter = DateTimeFormatter.ofPattern(fmt, Locale.getDefault())
    return formatter.format(this)
}