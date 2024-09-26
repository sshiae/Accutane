package com.example.accutane.domain.interactor

import com.example.accutane.data.local.AccutaneLocalRepository
import com.example.accutane.domain.model.AccutaneCourseModel
import javax.inject.Inject

/**
 * Interactor for interacting with various repositories
 */
class AccutaneInteractor @Inject constructor(
    private val localRepository: AccutaneLocalRepository
) {

    suspend fun getAccutaneCourses(): List<AccutaneCourseModel> {
        return localRepository.getAccutaneCourses()
    }

    suspend fun deleteAccutaneCourse(id: Long) {
        localRepository.deleteAccutaneCourse(id)
    }
}