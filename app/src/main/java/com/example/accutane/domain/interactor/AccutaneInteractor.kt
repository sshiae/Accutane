package com.example.accutane.domain.interactor

import com.example.accutane.data.local.AccutaneLocalRepository
import com.example.accutane.domain.model.AccutaneCourseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interactor for interacting with various repositories
 */
class AccutaneInteractor @Inject constructor(
    private val localRepository: AccutaneLocalRepository
) {
    fun getAccutaneCourses(): Flow<List<AccutaneCourseModel>> {
        return localRepository.getAccutaneCourses()
    }

    suspend fun deleteAccutaneCourse(id: Long?) {
        localRepository.deleteAccutaneCourse(id)
    }

    suspend fun getAccutaneCourseById(id: Long?): AccutaneCourseModel {
        return localRepository.getAccutaneCourseById(id)
    }

    suspend fun saveAccutaneCourse(model: AccutaneCourseModel) {
        localRepository.saveAccutaneCourse(model)
    }
}