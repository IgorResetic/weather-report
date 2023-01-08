package com.iresetic.weatherreport.weatherforcast.presentation

import android.content.Context
import com.iresetic.weatherreport.R
import com.iresetic.weatherreport.common.MainCoroutineRule
import com.iresetic.weatherreport.common.testCurrentTemperature
import com.iresetic.weatherreport.common.testHumidity
import com.iresetic.weatherreport.common.testWindSpeed
import com.iresetic.weatherreport.core.data.local.datasource.SimpleLocalDataSource
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.core.domain.usecases.GetSavedCity
import com.iresetic.weatherreport.core.util.DispatchersProvider
import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport
import com.iresetic.weatherreport.weatherforcast.domain.repositories.WeatherReportRepository
import com.iresetic.weatherreport.weatherforcast.domain.usecases.GetCityWeatherForecast
import com.iresetic.weatherreport.weatherforcast.presentation.model.UiCityWeatherReport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
class WeatherForecastViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getSavedCity: GetSavedCity
    private lateinit var getCityWeatherForecast: GetCityWeatherForecast
    private lateinit var mockSimpleLocalDataSource: SimpleLocalDataSource
    private lateinit var mockWeatherReportRepository: WeatherReportRepository
    @Mock private lateinit var mockContext: Context

    private lateinit var weatherForecastViewModel: WeatherForecastViewModel

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

    @Before
    fun setUp() {
        val dispatchersProvider = object : DispatchersProvider {
            override fun io() = mainCoroutineRule.dispatcher
        }

        mockSimpleLocalDataSource = mock()
        mockWeatherReportRepository = mock()
        mockContext = mock {
            on {getString(R.string.weather_forecast_io_error)} doReturn TEST_IO_EXCEPTION_MESSAGE
            on {getString(R.string.weather_forecast_api_error)} doReturn TEST_HTTP_EXCEPTION_MESSAGE
        }

        getSavedCity = GetSavedCity(mockSimpleLocalDataSource)
        getCityWeatherForecast = GetCityWeatherForecast(mockContext, mockWeatherReportRepository, dispatchersProvider)

        weatherForecastViewModel = WeatherForecastViewModel(getSavedCity, getCityWeatherForecast)
    }

    @Test
    fun onEvent_getCityWeatherReport_cityIsNotSelected() = runTest {
        whenever(mockSimpleLocalDataSource.getSelectedCity()).thenReturn(null)
        whenever(mockWeatherReportRepository.getCityWeatherReport(testCity)).thenReturn(testCityWeatherReport)

        weatherForecastViewModel.onEvent(WeatherForecastEvent.GetCityWeatherReport)
        advanceUntilIdle()

        val result = weatherForecastViewModel.state

        assertEquals(null, result.weatherReport)
    }


    @Test
    fun onEvent_getCityWeatherReport_cityIsSelectedAndWeatherReportIsReturnedSuccessfully() = runTest {
        val expectedResult = UiCityWeatherReport.fromDomain(testCityWeatherReport)
        whenever(mockSimpleLocalDataSource.getSelectedCity()).thenReturn(testCity)
        whenever(mockWeatherReportRepository.getCityWeatherReport(testCity)).thenReturn(testCityWeatherReport)

        weatherForecastViewModel.onEvent(WeatherForecastEvent.GetCityWeatherReport)
        advanceUntilIdle()

        val result = weatherForecastViewModel.state

        assertEquals(expectedResult, result.weatherReport)
    }

    @Test
    fun onEvent_getCityWeatherReport_cityIsSelected_IOException() = runTest {
        val expectedResult = UiCityWeatherReport.fromDomain(testEmptyCityWeatherReport)
        whenever(mockSimpleLocalDataSource.getSelectedCity()).thenReturn(testCity)

        whenever(mockWeatherReportRepository.getCityWeatherReport(testCity)).doAnswer { throw IOException() }
        whenever(mockWeatherReportRepository.getCityWeatherReportFromCache(testCity))
            .thenReturn(testEmptyCityWeatherReport)

        weatherForecastViewModel.onEvent(WeatherForecastEvent.GetCityWeatherReport)
        advanceUntilIdle()

        val result = weatherForecastViewModel.state

        assertEquals(expectedResult, result.weatherReport)
        assertEquals(TEST_IO_EXCEPTION_MESSAGE, result.error)
    }

    companion object {
        const val TEST_IO_EXCEPTION_MESSAGE = "io exception"
        const val TEST_HTTP_EXCEPTION_MESSAGE = "http exception"
        const val TEST_ID = "id_1"
        const val TEST_NAME = "one_name"
        const val TEST_LATITUDE = "1.0"
        const val TEST_LONGITUDE = "2.0"
    }
}