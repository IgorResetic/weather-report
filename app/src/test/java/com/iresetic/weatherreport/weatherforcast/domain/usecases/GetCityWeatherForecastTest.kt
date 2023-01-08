package com.iresetic.weatherreport.weatherforcast.domain.usecases

import android.content.Context
import com.iresetic.weatherreport.R
import com.iresetic.weatherreport.common.MainCoroutineRule
import com.iresetic.weatherreport.common.testCurrentTemperature
import com.iresetic.weatherreport.common.testHumidity
import com.iresetic.weatherreport.common.testWindSpeed
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.core.util.DispatchersProvider
import com.iresetic.weatherreport.core.util.Resource
import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport
import com.iresetic.weatherreport.weatherforcast.domain.repositories.WeatherReportRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class GetCityWeatherForecastTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val testCity = City(
        id = TEST_ID,
        name = TEST_NAME,
        latitude = TEST_LATITUDE,
        longitude = TEST_LONGITUDE
    )

    private val testCityWeatherReport = CityWeatherReport(
        id = TEST_ID,
        cityName = TEST_NAME,
        currentTemperature = testCurrentTemperature,
        humidity = testHumidity,
        windSpeed = testWindSpeed
    )

    private val testEmptyCityWeatherReport = CityWeatherReport.emptyCityWeatherReport().copy(
        id = TEST_ID,
        cityName = TEST_NAME
    )

    private lateinit var mockWeatherReportRepository: WeatherReportRepository
    @Mock private lateinit var mockContext: Context
    private lateinit var getCityWeatherForecast: GetCityWeatherForecast

    @Before
    fun setUp() {
        val dispatchersProvider = object : DispatchersProvider {
            override fun io() = mainCoroutineRule.dispatcher
        }

        mockWeatherReportRepository = mock()
        mockContext = mock {
            on {getString(R.string.weather_forecast_io_error)} doReturn TEST_IO_EXCEPTION_MESSAGE
            on {getString(R.string.weather_forecast_api_error)} doReturn TEST_HTTP_EXCEPTION_MESSAGE
        }

        getCityWeatherForecast = GetCityWeatherForecast(mockContext, mockWeatherReportRepository, dispatchersProvider)
    }

    @Test
    fun invoke_successfullyFetchCityWeatherReportFromApi() = runTest {
        whenever(mockWeatherReportRepository.getCityWeatherReport(testCity)).thenReturn(testCityWeatherReport)

        val result = getCityWeatherForecast.invoke(testCity)

        assertTrue(result is Resource.Success)
        assertEquals(testCityWeatherReport, result.data)
    }

    @Test
    fun invoke_failedToFetchCityWeatherReport_ioException() = runTest {
        whenever(mockWeatherReportRepository.getCityWeatherReport(testCity)).doAnswer{ throw  IOException() }
        whenever(mockWeatherReportRepository.getCityWeatherReportFromCache(testCity))
            .thenReturn(testEmptyCityWeatherReport)

        val result = getCityWeatherForecast.invoke(testCity)

        assertTrue(result is Resource.Error)
        assertEquals(testEmptyCityWeatherReport, result.data)
        assertEquals(TEST_IO_EXCEPTION_MESSAGE, result.message)
    }

    companion object {
        const val TEST_IO_EXCEPTION_MESSAGE = "io exception"
        const val TEST_HTTP_EXCEPTION_MESSAGE = "http exception"
        const val TEST_ID = "id"
        const val TEST_NAME = "name"
        const val TEST_LATITUDE = "1.0"
        const val TEST_LONGITUDE = "1.0"
    }
}