package com.konfio.domain.usecase

import arrow.core.Either
import com.konfio.domain.models.DogsDomain
import com.konfio.domain.models.DomainError

interface GetDogsUseCase {
    suspend operator fun invoke(): Either<DomainError, List<DogsDomain>>
}