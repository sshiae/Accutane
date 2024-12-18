package com.example.accutane.di

import android.content.Context
import androidx.room.Room
import com.example.accutane.common.AccutaneDatabase
import com.example.accutane.data.local.dao.AccutaneDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for the database
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @[Provides Singleton]
    fun provideRoomDatabase(@ApplicationContext context: Context): AccutaneDatabase {
        return Room.databaseBuilder(
            context,
            AccutaneDatabase::class.java,
            AccutaneDatabase.DATABASE_NAME)
            .build()
    }

    @[Provides Singleton]
    fun provideAccutaneDao(database: AccutaneDatabase): AccutaneDao {
        return database.accutaneDao()
    }
}