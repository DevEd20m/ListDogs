package com.konfio.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dogName: String,
    val description: String,
    val age: Int,
    val image: String
)
