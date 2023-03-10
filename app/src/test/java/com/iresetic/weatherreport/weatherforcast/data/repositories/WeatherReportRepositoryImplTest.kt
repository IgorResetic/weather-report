package com.iresetic.weatherreport.weatherforcast.data.repositories

import com.iresetic.weatherreport.common.testCurrentTemperature
import com.iresetic.weatherreport.common.testHumidity
import com.iresetic.weatherreport.common.testWeatherDto
import com.iresetic.weatherreport.common.testWindSpeed
import com.iresetic.weatherreport.core.constants.USER_AGENT
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.weatherforcast.data.api.WeatherReportApi
import com.iresetic.weatherreport.weatherforcast.data.cache.daos.WeatherReportDao
import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

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
        currentTemperature = testCurrentTemperature,
        humidity = testHumidity,
        windSpeed = testWindSpeed
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

        assertEquals(testCityWeatherReport, result)
    }

    @Test(expected = IOException::class )
    fun getCityWeatherReport_failedToFetchWeatherReport_throwHttpException() = runTest {
        whenever(mockWeatherReportApi.getWeatherReportData(
            USER_AGENT,
            MOCK_LATITUDE.toDouble(),
            MOCK_LONGITUDE.toDouble()
        )).doAnswer{ throw  IOException() }

        weatherReportRepository.getCityWeatherReport(testCity)
    }

    companion object {
        const val MOCK_ID = "1"
        const val MOCK_CITY_NAME = "Zagreb"
        const val MOCK_LATITUDE = "12.20"
        const val MOCK_LONGITUDE = "11.11"
    }
}