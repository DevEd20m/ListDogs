package com.konfio.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): KonfioDatabase =
        Room.databaseBuilder(
            context,
            KonfioDatabase::class.java,
            KonfioDatabase.DATABASE_NAME
        ).build()

    @Provides
    fun provideMyDao(db: KonfioDatabase): DogsDao = db.getDao()
}