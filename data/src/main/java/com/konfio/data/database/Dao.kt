package com.konfio.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.konfio.data.database.models.DogEntity

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogs(dogs: List<DogEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: DogEntity): Long

    @Query("SELECT * FROM DogEntity")
    suspend fun getDogs(): List<DogEntity>

    @Query("SELECT * FROM DogEntity WHERE id = :id")
    suspend fun getDogById(id: Int): DogEntity?

    @Delete
    suspend fun deleteDog(dog: DogEntity)
}