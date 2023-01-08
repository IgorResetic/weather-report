package com.iresetic.weatherreport.locationselection.domain.usecases

import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.locationselection.domain.repositories.CitiesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllCitiesTest {
    private lateinit var mockCitiesRepository: CitiesRepository
    private lateinit var getAllCities: GetAllCities

    private val testCity = City(
        id = TEST_ID,
        name = TEST_NAME,
        latitude = TEST_LATITUDE,
        longitude = TEST_LONGITUDE
    )

    @Before
    fun setUp() {
        mockCitiesRepository = mock()
        getAllCities = GetAllCities(mockCitiesRepository)
    }

    @Test
    fun invoke_successfullyGetAllCities() = runTest{
        whenever(mockCitiesRepository.getAllCities()).thenReturn(listOf(testCity))

        val result = getAllCities.invoke()

        assertEquals(listOf(testCity), result)
    }

    @Test
    fun invoke_failedToGetAllCities() = runTest{
        whenever(mockCitiesRepository.getAllCities()).thenReturn(null)

        val result = getAllCities.invoke()

        assertNull(result)
    }

    companion object {
        const val TEST_ID = "id"
        const val TEST_NAME = "name"
        const val TEST_LATITUDE = "1.0"
        const val TEST_LONGITUDE = "2.0"
    }
}