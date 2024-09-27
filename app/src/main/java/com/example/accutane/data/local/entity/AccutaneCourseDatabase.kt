package com.example.accutane.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity for accutane course
 */
@Entity(tableName = "accutane_course")
data class AccutaneCourseDatabase(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "daily_dose")
    val dailyDose: Double,
    @ColumnInfo(name = "total_target_dose")
    val totalTargetDose: Double,
    @ColumnInfo(name = "accumulated_course_dose")
    val accumulatedCourseDose: Double,
    @ColumnInfo(name = "appointment_reminder_time")
    val appointmentReminderTime: String,
    @ColumnInfo(name = "create_date")
    val createDate: String
)