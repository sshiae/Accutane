package com.example.accutane.data.local

import com.example.accutane.domain.model.AccutaneCourseModel

/**
 * Local repository for database access
 */
interface AccutaneLocalRepository {

    /**
     * Get a list of courses
     */
    suspend fun getAccutaneCourses(): List<AccutaneCourseModel>
}