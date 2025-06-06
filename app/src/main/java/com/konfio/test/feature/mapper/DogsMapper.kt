package com.konfio.test.feature.mapper

import com.konfio.domain.models.DogsDomain
import com.konfio.test.feature.dogsscreen.model.DogUi

fun DogsDomain.toUi(): DogUi {
    return DogUi(
        id = id,
        dogName = dogName,
        description = description,
        age = age,
        image = image
    )
}