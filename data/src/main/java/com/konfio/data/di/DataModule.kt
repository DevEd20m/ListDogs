package com.konfio.data.di

import com.konfio.data.RepositoryImpl
import com.konfio.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindRepository(impl: RepositoryImpl): Repository
}