package com.konfio.test.di

import com.konfio.domain.GetDogsUseCase
import com.konfio.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetDogsUseCase(repository: Repository): GetDogsUseCase = GetDogsUseCase(repository)
}