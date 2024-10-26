package com.example.accutane.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.accutane.data.local.entity.AccutaneCourseDatabase
import kotlinx.coroutines.flow.Flow

/**
 * DAO to work with the database
 */
@Dao
interface AccutaneDao {
    @Query("SELECT * FROM accutane_course")
    fun getAccutaneCourses(): Flow<List<AccutaneCourseDatabase>>

    @Query("DELETE FROM accutane_course WHERE id = :id")
    suspend fun deleteAccutaneCourse(id: Long?)

    @Query("SELECT * FROM accutane_course WHERE id = :id")
    suspend fun getAccutaneCourseById(id: Long?): AccutaneCourseDatabase

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAccutaneCourse(model: AccutaneCourseDatabase)
}