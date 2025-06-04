package com.konfio.data.network

import com.konfio.data.network.models.DogRequest
import retrofit2.http.GET

interface Api{

    @GET("/products")
    suspend fun getDogs(): List<DogRequest>
}