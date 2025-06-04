package com.konfio.data

import com.konfio.data.database.models.DogEntity
import com.konfio.data.network.models.DogRequest
import com.konfio.domain.DogsDomain

fun DogsDomain.toEntity(): DogEntity = DogEntity(
    dogName = dogName,
    description = description,
    age = age,
    image = image
)

fun DogRequest.toDomain() = DogsDomain(
    id = 0,
    dogName = dogName.orEmpty(),
    description = description.orEmpty(),
    age = age ?: 0,
    image = image.orEmpty()
)

fun DogEntity.toDomain() = DogsDomain(
    id = id,
    dogName = dogName,
    description = description,
    age = age,
    image = image
)