package com.example.accutane

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.getCurrentRusDate(): String {
    val currentDate = this
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    return dateFormat.format(currentDate)
}