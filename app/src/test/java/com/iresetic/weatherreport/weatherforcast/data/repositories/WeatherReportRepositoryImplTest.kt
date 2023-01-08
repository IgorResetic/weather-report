package com.iresetic.weatherreport.weatherforcast.data.repositories

import com.iresetic.weatherreport.common.testWeatherDto
import com.iresetic.weatherreport.core.constants.USER_AGENT
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.weatherforcast.data.api.WeatherReportApi
import com.iresetic.weatherreport.weatherforcast.data.cache.daos.WeatherReportDao
import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherReportRepositoryImplTest {
    private val testCity = City(
        id = MOCK_ID,
        name = MOCK_CITY_NAME,
        latitude = MOCK_LATITUDE,
        longitude = MOCK_LONGITUDE
    )

    private val testCityWeatherReport = CityWeatherReport(
        id = MOCK_ID,
        cityName = MOCK_CITY_NAME,
        currentTemperature =
    )

    private lateinit var mockWeatherReportApi: WeatherReportApi
    private lateinit var mockWeatherReportDao: WeatherReportDao

    private lateinit var weatherReportRepository: WeatherReportRepositoryImpl

    @Before
    fun setUp() {
        mockWeatherReportApi = mock()
        mockWeatherReportDao = mock()

        weatherReportRepository = WeatherReportRepositoryImpl(
            mockWeatherReportApi,
            mockWeatherReportDao
        )
    }

    @Test
    fun getCityWeatherReport_weatherReportReturned() = runTest {
        whenever(mockWeatherReportApi.getWeatherReportData(
            USER_AGENT,
            MOCK_LATITUDE.toDouble(),
            MOCK_LONGITUDE.toDouble()
        )).thenReturn(testWeatherDto)

        val result = weatherReportRepository.getCityWeatherReport(testCity)

        assertEquals(result, "")
    }

    companion object {
        const val MOCK_USER_AGENT = "User Agent"
        const val MOCK_ID = "1"
        const val MOCK_CITY_NAME = "Zagreb"
        const val MOCK_LATITUDE = "12.20"
        const val MOCK_LONGITUDE = "11.11"
    }
}