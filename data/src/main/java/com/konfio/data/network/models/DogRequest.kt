package com.konfio.data.network.models

data class DogRequest(
    val dogName: String? = null,
    val description: String? = null,
    val age: Int? = null,
    val image: String? = null
)