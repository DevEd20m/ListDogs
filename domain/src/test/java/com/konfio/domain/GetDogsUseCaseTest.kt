package com.konfio.domain

import arrow.core.Either
import com.konfio.domain.models.DogsDomain
import com.konfio.domain.models.DomainError
import com.konfio.domain.repository.DogsRepository
import com.konfio.domain.usecase.GetDogsUseCase
import com.konfio.domain.usecase.GetDogsUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class GetDogsUseCaseTest {
    @MockK
    private lateinit var repository: DogsRepository
    private lateinit var useCase: GetDogsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetDogsUseCaseImpl(repository)
    }

    @After
    fun tearDown() = clearAllMocks()

    @Test
    fun `when local dogs are available, should return local dogs`() = runTest {
        // Given
        val localDogs = listOf(DogsDomain(1, dogName = "", description = "", age = 1, image = ""))
        coEvery { repository.getLocalDogs() } returns Either.Right(localDogs)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isRight())
        assertEquals(localDogs, result.getOrNull())

        coVerify(exactly = 1) { repository.getLocalDogs() }
        coVerify(exactly = 0) { repository.getRemoteDogs() }
        coVerify(exactly = 0) { repository.saveDogsInLocal(any()) }
    }

    @Test
    fun `when local dogs are empty, should fetch remote dogs, save them and return remote dogs`() = runTest {
        // Given
        val remoteDogs = listOf(DogsDomain(2, dogName = "", description = "", age = 1, image = ""))
        coEvery { repository.getLocalDogs() } returns Either.Right(emptyList())
        coEvery { repository.getRemoteDogs() } returns Either.Right(remoteDogs)
        coEvery { repository.saveDogsInLocal(remoteDogs) } returns Either.Right(listOf())

        // When
        val result = useCase()

        // Then
        assertTrue(result.isRight())
        assertEquals(remoteDogs, result.getOrNull())

        coVerifyOrder {
            repository.getLocalDogs()
            repository.getRemoteDogs()
            repository.saveDogsInLocal(remoteDogs)
        }
    }

    @Test
    fun `when local dogs returns error, should return DomainError`() = runTest {
        // Given
        val domainError = DomainError.DataBaseError
        coEvery { repository.getLocalDogs() } returns Either.Left(domainError)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isLeft())
        assertEquals(domainError, result.leftOrNull())

        coVerify(exactly = 1) { repository.getLocalDogs() }
        coVerify(exactly = 0) { repository.getRemoteDogs() }
        coVerify(exactly = 0) { repository.saveDogsInLocal(any()) }
    }

    @Test
    fun `when remote dogs returns error, should return DomainError`() = runTest {
        // Given
        val domainError = DomainError.Network
        coEvery { repository.getLocalDogs() } returns Either.Right(emptyList())
        coEvery { repository.getRemoteDogs() } returns Either.Left(domainError)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isLeft())
        assertEquals(domainError, result.leftOrNull())

        coVerifyOrder {
            repository.getLocalDogs()
            repository.getRemoteDogs()
        }
        coVerify(exactly = 0) { repository.saveDogsInLocal(any()) }
    }

    @Test
    fun `when saving remote dogs returns error, should return DomainError`() = runTest {
        // Given
        val remoteDogs = listOf(DogsDomain(3, dogName = "", description = "", age = 1, image = ""))
        val domainError = DomainError.DataBaseError

        coEvery { repository.getLocalDogs() } returns Either.Right(emptyList())
        coEvery { repository.getRemoteDogs() } returns Either.Right(remoteDogs)
        coEvery { repository.saveDogsInLocal(remoteDogs) } returns Either.Left(domainError)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isLeft())
        assertEquals(domainError, result.leftOrNull())

        coVerifyOrder {
            repository.getLocalDogs()
            repository.getRemoteDogs()
            repository.saveDogsInLocal(remoteDogs)
        }
    }
}