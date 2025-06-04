package com.konfio.domain

import com.konfio.domain.repository.Repository

class GetDogsUseCase(private val repository: Repository) {
    suspend operator fun invoke() = repository.getDogs()
}