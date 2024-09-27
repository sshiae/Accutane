package com.example.accutane.domain.model

/**
 * The model for the course in the app
 */
data class AccutaneCourseModel(
    val id: Long?,
    val name: String,
    val dailyDose: Double,
    val totalTargetDose: Double,
    val accumulatedCourseDose: Double,
    val appointmentReminderTime: String,
    val createDate: String
)