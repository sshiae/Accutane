package com.example.accutane.common

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.accutane.common.AccutaneDatabase.Companion.DATABASE_VERSION
import com.example.accutane.data.local.dao.AccutaneDao
import com.example.accutane.data.local.entity.AccutaneCourseDatabase

/**
 * Database for the application
 */
@TypeConverters(DateConverter::class)
@Database(
    entities = [AccutaneCourseDatabase::class],
    version = DATABASE_VERSION
)
abstract class AccutaneDatabase : RoomDatabase() {
    abstract fun accutaneDao(): AccutaneDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "AccutaneDatabase"
    }
}