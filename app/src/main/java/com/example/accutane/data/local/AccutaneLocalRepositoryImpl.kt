package com.example.accutane.data.local

import com.example.accutane.data.local.dao.AccutaneDao
import com.example.accutane.data.local.mapper.toDatabase
import com.example.accutane.data.local.mapper.toModel
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

    override suspend fun deleteAccutaneCourse(id: Long?) {
        accutaneDao.deleteAccutaneCourse(id)
    }

    override suspend fun getAccutaneCourseById(id: Long?): AccutaneCourseModel {
        return accutaneDao.getAccutaneCourseById(id).toModel()
    }

    override suspend fun saveAccutaneCourse(model: AccutaneCourseModel) {
        accutaneDao.saveAccutaneCourse(model.toDatabase())
    }
}