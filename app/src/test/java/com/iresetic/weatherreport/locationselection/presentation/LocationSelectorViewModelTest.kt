package com.iresetic.weatherreport.locationselection.presentation

import com.iresetic.weatherreport.common.MainCoroutineRule
import com.iresetic.weatherreport.core.data.local.datasource.SimpleLocalDataSource
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.core.domain.usecases.SaveSelectCity
import com.iresetic.weatherreport.core.util.DispatchersProvider
import com.iresetic.weatherreport.locationselection.domain.repositories.CitiesRepository
import com.iresetic.weatherreport.locationselection.domain.usecases.GetAllCities
import com.iresetic.weatherreport.locationselection.domain.usecases.GetCityData
import com.iresetic.weatherreport.locationselection.presentation.model.UICity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LocationSelectorViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getAllCities: GetAllCities
    private lateinit var getCityData: GetCityData
    private lateinit var saveSelectCity: SaveSelectCity
    private lateinit var mockCitiesRepository: CitiesRepository
    private lateinit var mockSimpleLocalDataSource: SimpleLocalDataSource
    private lateinit var locationSelectorViewModel: LocationSelectorViewModel

    private val testCity = City(
        id = TEST_ID,
        name = TEST_NAME,
        latitude = TEST_LATITUDE,
        longitude = TEST_LONGITUDE
    )

    private val testCityTwo = City(
        id = TEST_ID_TWO,
        name = TEST_NAME_TWO,
        latitude = TEST_LATITUDE_TWO,
        longitude = TEST_LONGITUDE_TWO
    )

    private val cities = listOf(testCity, testCityTwo)
    private val uiCities = cities.map { UICity.fromDomain(it) }

    @Before
    fun setUp() {
        val dispatchersProvider = object : DispatchersProvider {
            override fun io() = mainCoroutineRule.dispatcher
        }

        mockCitiesRepository = mock()
        mockSimpleLocalDataSource = mock()
        getAllCities = GetAllCities(mockCitiesRepository, dispatchersProvider)
        getCityData = GetCityData(mockCitiesRepository, dispatchersProvider)
        saveSelectCity = SaveSelectCity(mockSimpleLocalDataSource, dispatchersProvider)

        locationSelectorViewModel = LocationSelectorViewModel(
            getAllCities,
            getCityData,
            saveSelectCity
        )
    }

    @Test
    fun onEvent_getCities_successfullyGetCities() = runTest {
        populateCities()

        val result = locationSelectorViewModel.state

        assertFalse(result.isLoading)
        assertEquals(uiCities, result.cities)
    }

    @Test
    fun onEvent_selectCity_cityIsSelected() = runTest {
        whenever(mockCitiesRepository.getCityData(TEST_ID)).thenReturn(testCity)
        val testUiCity = UICity.fromDomain(testCity)

        locationSelectorViewModel.onEvent(LocationSelectorEvent.SelectCity(testUiCity))
        advanceUntilIdle()

        val result = locationSelectorViewModel.state

        assertFalse(result.isLoading)
        verify(mockSimpleLocalDataSource).setSelectedCity(testCity)
    }

    @Test
    fun onEvent_searchForCity_searchValueIsEmpty() = runTest {
        val searchValue = ""
        populateCities()
        searchForCity(searchValue)

        val result = locationSelectorViewModel.state

        assertFalse(result.isLoading)
        assertEquals(searchValue, result.searchedCityValue)
        assertEquals(uiCities, result.cities)
    }

    @Test
    fun onEvent_searchForCity_searchValueIsNotEmpty() = runTest {
        val searchValue = "two"
        val expectedUiCities = listOf(UICity.fromDomain(testCityTwo))
        populateCities()
        searchForCity(searchValue)

        val result = locationSelectorViewModel.state

        assertFalse(result.isLoading)
        assertEquals(searchValue, result.searchedCityValue)
        assertEquals(expectedUiCities, result.cities)
    }

    @Test
    fun onEvent_clearSearchBarText() = runTest {
        val expectedUiCities = listOf(UICity.fromDomain(testCityTwo))
        populateCities()
        searchForCity("two")

        var result = locationSelectorViewModel.state
        assertEquals(expectedUiCities, result.cities)


        locationSelectorViewModel.onEvent(LocationSelectorEvent.ClearSearchBarText)
        advanceUntilIdle()
        result = locationSelectorViewModel.state

        assertEquals(uiCities, result.cities)
    }

    private fun populateCities() = runTest {
        whenever(mockCitiesRepository.getAllCities()).thenReturn(cities)
        locationSelectorViewModel.onEvent(LocationSelectorEvent.GetCities)
        advanceUntilIdle()
    }

    private fun searchForCity(searchValue: String) = runTest {
        locationSelectorViewModel.onEvent(LocationSelectorEvent.SearchForCity(searchValue))
        advanceUntilIdle()
    }

    companion object {
        const val TEST_ID = "id_1"
        const val TEST_ID_TWO = "id_2"
        const val TEST_NAME = "one_name"
        const val TEST_NAME_TWO = "two_city"
        const val TEST_LATITUDE = "1.0"
        const val TEST_LATITUDE_TWO = "2.0"
        const val TEST_LONGITUDE = "2.0"
        const val TEST_LONGITUDE_TWO = "2.0"
    }
}