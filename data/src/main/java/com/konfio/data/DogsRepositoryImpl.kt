package com.konfio.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.konfio.data.database.DogsDao
import com.konfio.data.exception.DataError
import com.konfio.data.exception.toDomainError
import com.konfio.data.network.DogsApi
import com.konfio.data.network.models.DogRequest
import com.konfio.domain.models.DogsDomain
import com.konfio.domain.models.DomainError
import com.konfio.domain.repository.DogsRepository
import javax.inject.Inject


/**
 * Implementación de DogsRepository.
 *
 * Tiene como finalidad la consulta a las diferentes fuentes de datos, ya sea remoto o local
 * y realiza el mapeo de errores de DataError a DomainError.
 *
 * @property api DogsApi para la consulta de mascotas en remoto .
 * @property dao DogsDao para la consulta y persistencia de mascotas en local.
 */


class DogsRepositoryImpl @Inject constructor(
    private val dao: DogsDao,
    private val api: DogsApi
) :
    DogsRepository {


    override suspend fun getRemoteDogs(): Either<DomainError, List<DogsDomain>> {
        return when (val result = getRemoteDogsData()) {
            is Either.Left -> result.value.toDomainError().left()
            is Either.Right -> result.value.map { it.toDomain() }.right()
        }
    }

    private suspend fun getRemoteDogsData(): Either<DataError, List<DogRequest>> {
        val response = api.getDogs()
        return if (!response.isSuccessful) {
            DataError.Server.left()
        } else {
            val body = response.body()
            body?.right() ?: DataError.Parsing.left()
        }
    }

    override suspend fun getLocalDogs(): Either<DomainError, List<DogsDomain>> {
        return try {
            val dogs = dao.getDogs().map { it.toDomain() }
            dogs.right()
        } catch (e: Exception) {
            DomainError.DataBaseError.left()
        }
    }

    override suspend fun saveDogsInLocal(dogs: List<DogsDomain>): Either<DomainError, List<Long>> {
        return try {
            val result = dao.insertDogs(dogs.map { it.toEntity() })
            result.right()
        } catch (e: Exception) {
            DomainError.DataBaseError.left()
        }
    }

}