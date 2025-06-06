package com.konfio.domain.repository

import arrow.core.Either
import com.konfio.domain.models.DogsDomain
import com.konfio.domain.models.DomainError

interface DogsRepository {
    suspend fun getLocalDogs(): Either<DomainError, List<DogsDomain>>
    suspend fun getRemoteDogs(): Either<DomainError, List<DogsDomain>>
    suspend fun saveDogsInLocal(dogs: List<DogsDomain>): Either<DomainError, List<Long>>
}