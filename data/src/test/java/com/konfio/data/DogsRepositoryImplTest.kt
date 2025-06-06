package com.konfio.data

import com.konfio.data.database.DogsDao
import com.konfio.data.database.models.DogEntity
import com.konfio.data.network.DogsApi
import com.konfio.data.network.models.DogRequest
import com.konfio.domain.models.DogsDomain
import com.konfio.domain.models.DomainError
import com.konfio.domain.repository.DogsRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class DogsRepositoryImplTest {
    @MockK
    private lateinit var dao: DogsDao

    @MockK
    private lateinit var api: DogsApi

    private lateinit var repository: DogsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = DogsRepositoryImpl(dao, api)
    }

    @After
    fun tearDown() = clearAllMocks()

    @Test
    fun ` when api returns success should return DomainRight with mapped list`() = runTest {
        // Given
        val dogRequests = listOf(DogRequest(dogName = "", description = "", age = 1, image = ""))
        val dogsDomain = dogRequests.map { it.toDomain() }

        coEvery { api.getDogs() } returns Response.success(dogRequests)

        // When
        val result = repository.getRemoteDogs()

        // Then
        assertTrue(result.isRight())
        assertEquals(dogsDomain, result.getOrNull())
    }

    @Test
    fun ` when api returns server error should return DomainError Server`() = runTest {
        // Given
        coEvery { api.getDogs() } returns Response.error(500, "".toResponseBody())

        // When
        val result = repository.getRemoteDogs()

        // Then
        assertTrue(result.isLeft())
        assertEquals(DomainError.Server, result.leftOrNull())
    }

    @Test
    fun `when api returns null body should return DomainError Parsing`() = runTest {
        // Given
        coEvery { api.getDogs() } returns Response.success(null)

        // When
        val result = repository.getRemoteDogs()

        // Then
        assertTrue(result.isLeft())
        assertEquals(DomainError.Parsing, result.leftOrNull())
    }

    @Test
    fun `when database tries to get local dogs and is success it should return list dogDomain`() = runTest {
        // Given
        val entities = listOf(DogEntity(id = 1, dogName = "", description = "", age = 1, image = ""))
        val expected = entities.map { it.toDomain() }

        coEvery { dao.getDogs() } returns entities

        // When
        val result = repository.getLocalDogs()

        // Then
        assertTrue(result.isRight())
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `when database tries to get list throws exception should return DomainError Unknown`() = runTest {
        // Given
        coEvery { dao.getDogs() } throws RuntimeException("DB error")

        // When
        val result = repository.getLocalDogs()

        // Then
        assertTrue(result.isLeft())
        assertTrue(result.leftOrNull() is DomainError.DataBaseError)
    }

    @Test
    fun `when database tries to insert is succeeds should return list id's`() = runTest {
        // Given
        val dogs = listOf(DogsDomain(id = 1, dogName = "", description = "", age = 1, image = ""))
        val expectedEntities = dogs.map { it.toEntity() }
        val expectedIds = listOf(1L)

        coEvery { dao.insertDogs(expectedEntities) } returns expectedIds

        // When
        val result = repository.saveDogsInLocal(dogs)

        // Then
        assertTrue(result.isRight())
        assertEquals(expectedIds, result.getOrNull())
    }

    @Test
    fun `when database tries to insert and throws exception should return DomainError Unknown`() =
        runTest {
            // Given
            val dogs = listOf(DogsDomain(id = 1, dogName = "", description = "", age = 1, image = ""))

            coEvery { dao.insertDogs(any()) } throws RuntimeException("DB error")

            // When
            val result = repository.saveDogsInLocal(dogs)

            // Then
            assertTrue(result.isLeft())
            assertTrue(result.leftOrNull() is DomainError.DataBaseError)
        }
}