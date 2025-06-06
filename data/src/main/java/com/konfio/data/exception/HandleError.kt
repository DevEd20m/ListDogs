package com.konfio.data.exception

import com.konfio.domain.models.DomainError

sealed class DataError {
    data object Network : DataError()
    data object Server : DataError()
    data object Database : DataError()
    data object Timeout : DataError()
    data object Parsing : DataError()
}

fun DataError.toDomainError(): DomainError = when (this) {
    DataError.Network -> DomainError.Network
    DataError.Server -> DomainError.Server
    DataError.Parsing -> DomainError.Parsing
    DataError.Database -> DomainError.DataBaseError
    DataError.Timeout -> DomainError.Timeout
}