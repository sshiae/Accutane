package com.example.accutane

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.accutane.domain.model.AccutaneCourseFilterModel

fun SnapshotStateList<AccutaneCourseFilterModel>.getOrDefault(key: String, default: Boolean): Boolean {
    val foundValue: AccutaneCourseFilterModel? = firstOrNull { item -> item.key == key }
    if (foundValue == null) {
        return default
    }
    return foundValue.value
}

fun SnapshotStateList<AccutaneCourseFilterModel>.set(key: String, value: Boolean) {
    val foundValue: AccutaneCourseFilterModel? = firstOrNull { item -> item.key == key }
    if (foundValue == null) {
        return
    }
    foundValue.value = value
}