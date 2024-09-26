package com.example.accutane.data.local

import com.example.accutane.data.local.dao.AccutaneDao
import com.example.accutane.data.local.mapper.toModels
import com.example.accutane.domain.model.AccutaneCourseModel
import javax.inject.Inject

/**
 * Implementation of [AccutaneLocalRepository]
 */
class AccutaneLocalRepositoryImpl @Inject constructor(
    private val accutaneDao: AccutaneDao
) : AccutaneLocalRepository {

    override suspend fun getAccutaneCourses(): List<AccutaneCourseModel> {
        return accutaneDao.getAccutaneCourses().toModels()
    }

    override suspend fun deleteAccutaneCourse(id: Long) {
        accutaneDao.deleteAccutaneCourse(id)
    }
}