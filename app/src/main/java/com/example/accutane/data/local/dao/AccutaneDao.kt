package com.example.accutane.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.accutane.data.local.entity.AccutaneCourseDatabase

/**
 * DAO to work with the database
 */
@Dao
interface AccutaneDao {

    @Query("SELECT * FROM accutane_course")
    suspend fun getAccutaneCourses(): List<AccutaneCourseDatabase>

    @Query("DELETE FROM accutane_course WHERE id = :id")
    suspend fun deleteAccutaneCourse(id: Long)
}