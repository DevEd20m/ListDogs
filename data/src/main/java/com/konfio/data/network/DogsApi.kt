package com.konfio.data.network

import com.konfio.data.network.models.DogRequest
import retrofit2.Response
import retrofit2.http.GET

interface DogsApi{

    @GET("1151549092634943488")
    suspend fun getDogs(): Response<List<DogRequest>>
}