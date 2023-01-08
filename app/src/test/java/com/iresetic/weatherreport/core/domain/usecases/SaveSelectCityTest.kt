package com.iresetic.weatherreport.core.domain.usecases

import com.iresetic.weatherreport.core.data.local.datasource.SimpleLocalDataSource
import com.iresetic.weatherreport.core.domain.model.city.City
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class SaveSelectCityTest {
    private lateinit var mockLocalDataSource: SimpleLocalDataSource
    private lateinit var saveSelectCity: SaveSelectCity

    private val testCity = City(
        id = GetSavedCityTest.TEST_ID,
        name = GetSavedCityTest.TEST_NAME,
        latitude = GetSavedCityTest.TEST_LATITUDE,
        longitude = GetSavedCityTest.TEST_LONGITUDE
    )

    @Before
    fun setUp() {
        mockLocalDataSource = mock()
        saveSelectCity = SaveSelectCity(mockLocalDataSource)
    }

    @Test
    fun invoke_successfullySaveSelectedCityToLocalDataSource() = runTest {
        saveSelectCity.invoke(testCity)

        verify(mockLocalDataSource).setSelectedCity(testCity)
    }
}