package com.example.accutane.data.local.mapper

import com.example.accutane.data.local.entity.AccutaneCourseDatabase
import com.example.accutane.domain.model.AccutaneCourseModel

fun List<AccutaneCourseDatabase>.toModels(): List<AccutaneCourseModel> {
    return map { it.toModel() }
}

fun AccutaneCourseDatabase.toModel(): AccutaneCourseModel {
    return AccutaneCourseModel(
        id = id,
        name = name,
        dailyDose = dailyDose,
        totalTargetDose = totalTargetDose,
        accumulatedCourseDose = accumulatedCourseDose,
        appointmentReminderTime = appointmentReminderTime,
        createDate = createDate
    )
}