package com.konfio.domain.models

sealed class DomainError {
    data object Network : DomainError()
    data object Server : DomainError()
    data object DataBaseError : DomainError()
    data object Timeout : DomainError()
    data object Parsing : DomainError()
    data object Unknown : DomainError()
}

