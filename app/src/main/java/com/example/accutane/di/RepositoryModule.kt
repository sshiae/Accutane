package com.example.accutane.di

import com.example.accutane.data.local.AccutaneLocalRepository
import com.example.accutane.data.local.AccutaneLocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for binds repositories
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @[Binds Singleton]
    abstract fun bindAccutaneLocalRepository(
        accutaneLocalRepositoryImpl: AccutaneLocalRepositoryImpl
    ): AccutaneLocalRepository
}