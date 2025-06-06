package com.konfio.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.konfio.data.database.models.DogEntity

@Dao
interface DogsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogs(dogs: List<DogEntity>): List<Long>

    @Query("SELECT * FROM DogEntity")
    suspend fun getDogs(): List<DogEntity>

}