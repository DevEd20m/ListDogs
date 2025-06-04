package com.konfio.data

import com.konfio.data.database.Dao
import com.konfio.data.network.Api
import com.konfio.domain.DogsDomain
import com.konfio.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val dao: Dao, private val api: Api) : Repository {

    override suspend fun getDogs(): List<DogsDomain> {
        val localDogs = dao.getDogs()
        return if (localDogs.isNotEmpty()) {
            localDogs.map { it.toDomain() }
        } else {
            val remoteDogs = api.getDogs().map { it.toDomain() }
            dao.insertDogs(remoteDogs.map { it.toEntity() })
            remoteDogs
        }
    }
}