package com.konfio.domain.repository

import com.konfio.domain.DogsDomain

interface Repository {
    suspend fun getDogs(): List<DogsDomain>
}