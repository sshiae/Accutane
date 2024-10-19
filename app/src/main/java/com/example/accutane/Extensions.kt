package com.example.accutane

import com.example.accutane.domain.model.AccutaneCourseFilterModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun List<AccutaneCourseFilterModel>.getOrDefault(key: String, default: Boolean): Boolean {
    val foundValue: AccutaneCourseFilterModel? = firstOrNull { item -> item.key == key }
    if (foundValue == null) {
        return default
    }
    return foundValue.value
}

fun List<AccutaneCourseFilterModel>.set(key: String, value: Boolean) {
    val foundValue: AccutaneCourseFilterModel? = firstOrNull { item -> item.key == key }
    if (foundValue == null) {
        return
    }
    foundValue.value = value
}

fun Date.getCurrentRusDate(): String {
    val currentDate = this
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    return dateFormat.format(currentDate)
}