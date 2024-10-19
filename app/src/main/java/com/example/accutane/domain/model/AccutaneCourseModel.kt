package com.example.accutane.domain.model

import java.util.Date
import java.util.concurrent.TimeUnit

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
    val createDate: Date,
    val terminated: Boolean,
    val remainingDays: Int,
    val treatmentDay: Long,
    val percentage: Float
) {
    /**
     * Returns the number of days left before treatment
     */
    fun calculateRemainingDays(): Int {
        val remainingDose = totalTargetDose - accumulatedCourseDose
        if (remainingDose <= 0) {
            return 0
        }
        val remainingDays = (remainingDose / dailyDose).toInt()
        return remainingDays
    }

    /**
     * Returns the number of days of treatment
     */
    fun calculateTreatmentDay(): Long {
        val today = Date()
        val diff: Long = today.time - createDate.time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
    }

    /**
     * Returns the percentage of course completion
     */
    fun calculatePercentage(): Float {
        return Math.round(((accumulatedCourseDose / totalTargetDose) * 100)).toFloat()
    }
}