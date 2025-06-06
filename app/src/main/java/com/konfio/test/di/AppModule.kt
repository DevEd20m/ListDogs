package com.konfio.test.di

import com.konfio.domain.repository.DogsRepository
import com.konfio.domain.usecase.GetDogsUseCase
import com.konfio.domain.usecase.GetDogsUseCaseImpl
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
    fun provideGetDogsUseCase(repository: DogsRepository): GetDogsUseCase = GetDogsUseCaseImpl(repository)
}