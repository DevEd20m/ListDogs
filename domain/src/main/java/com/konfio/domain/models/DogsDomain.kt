package com.konfio.domain.models

data class DogsDomain(
    val id: Int = 0,
    val dogName: String,
    val description: String,
    val age: Int,
    val image: String
)
