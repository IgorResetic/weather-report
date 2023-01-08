package com.iresetic.weatherreport.core.domain.usecases

import com.iresetic.weatherreport.core.data.local.datasource.SimpleLocalDataSource
import com.iresetic.weatherreport.core.domain.model.city.City
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetSavedCityTest {
    private lateinit var mockLocalDataSource: SimpleLocalDataSource
    private lateinit var getSavedCity: GetSavedCity

    private val testCity = City(
        id = TEST_ID,
        name = TEST_NAME,
        latitude = TEST_LATITUDE,
        longitude = TEST_LONGITUDE
    )

    @Before
    fun setUp() {
        mockLocalDataSource = mock()
        getSavedCity = GetSavedCity(mockLocalDataSource)
    }

    @Test
    fun invoke_successfullyGetSelectedCity() = runTest{
        whenever(mockLocalDataSource.getSelectedCity()).thenReturn(testCity)

        val result = getSavedCity.invoke()

        assertEquals(testCity, result)
    }

    @Test
    fun invoke_getSelectedCity_returnsNull() = runTest {
        whenever(mockLocalDataSource.getSelectedCity()).thenReturn(null)

        val result = getSavedCity.invoke()

        assertNull(result)
    }

    companion object {
        const val TEST_ID = "id"
        const val TEST_NAME = "name"
        const val TEST_LATITUDE = "1.0"
        const val TEST_LONGITUDE = "2.0"
    }
}