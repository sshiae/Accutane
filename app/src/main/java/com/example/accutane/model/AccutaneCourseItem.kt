package com.example.accutane.model

/**
 * The model for the course in the app
 */
data class AccutaneCourseItem(
    val id: String,
    val name: String,
    val dailyDose: Double,
    val totalTargetDose: Double,
    val accumulatedCourseDose: Double,
    val appointmentReminderTime: String,
    val createDate: String
)