package com.example.accutane.data.local

import com.example.accutane.domain.model.AccutaneCourseModel
import kotlinx.coroutines.flow.Flow

/**
 * Local repository for database access
 */
interface AccutaneLocalRepository {

    /**
     * Get a list of courses
     */
    fun getAccutaneCourses(): Flow<List<AccutaneCourseModel>>

    /**
     * Delete a course
     */
    suspend fun deleteAccutaneCourse(id: Long?)

    /**
     * Get a course by ID
     */
    suspend fun getAccutaneCourseById(id: Long?): AccutaneCourseModel

    /**
     * Save accutane course
     */
    suspend fun saveAccutaneCourse(model: AccutaneCourseModel)
}