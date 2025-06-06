package com.konfio.data.di

import com.konfio.data.DogsRepositoryImpl
import com.konfio.domain.repository.DogsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDogRepository(impl: DogsRepositoryImpl): DogsRepository

}