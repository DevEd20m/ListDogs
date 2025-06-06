package com.konfio.domain.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.konfio.domain.models.DogsDomain
import com.konfio.domain.models.DomainError
import com.konfio.domain.repository.DogsRepository
import javax.inject.Inject

/**
 * GetDogsUseCaseImpl tiene la tarea de obtener la lista de mascotas.
 * - Si hay mascotas en local, devuelve los locales.
 * - Si no hay en local, hace fetch remoto, guarda en la base de datos local y devuelve los remotos.
 */

class GetDogsUseCaseImpl @Inject constructor(
    private val repository: DogsRepository
) : GetDogsUseCase {

    override suspend operator fun invoke(): Either<DomainError, List<DogsDomain>> {
        return repository.getLocalDogs().flatMap { localDogs ->
            if (localDogs.isNotEmpty()) {
                Either.Right(localDogs)
            } else {
                repository.getRemoteDogs().flatMap { remoteDogs ->
                    repository.saveDogsInLocal(remoteDogs).map { _ -> remoteDogs }
                }
            }
        }
    }
}