package com.iresetic.weatherreport.locationselection.domain.usecases

import com.iresetic.weatherreport.common.MainCoroutineRule
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.core.util.DispatchersProvider
import com.iresetic.weatherreport.locationselection.domain.repositories.CitiesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetCityDataTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mockCitiesRepository: CitiesRepository
    private lateinit var getSavedCityData: GetCityData

    private val testCity = City(
        id = TEST_ID,
        name = TEST_NAME,
        latitude = TEST_LATITUDE,
        longitude = TEST_LONGITUDE
    )

    @Before
    fun setUp() {
        val dispatchersProvider = object : DispatchersProvider {
            override fun io() = mainCoroutineRule.dispatcher
        }

        mockCitiesRepository = mock()
        getSavedCityData = GetCityData(mockCitiesRepository, dispatchersProvider)
    }

    @Test
    fun invoke_successfullyGetSavedCityData() = runTest {
        whenever(mockCitiesRepository.getCityData(TEST_ID)).thenReturn(testCity)

        val result = getSavedCityData.invoke(TEST_ID)

        assertEquals(testCity, result)
    }

    @Test
    fun invoke_failedTOGetSavedCityData() = runTest {
        whenever(mockCitiesRepository.getCityData(TEST_ID)).thenReturn(null)

        val result = getSavedCityData.invoke(TEST_ID)

        assertNull(result)
    }

    companion object {
        const val TEST_ID = "id"
        const val TEST_NAME = "name"
        const val TEST_LATITUDE = "1.0"
        const val TEST_LONGITUDE = "2.0"
    }
}