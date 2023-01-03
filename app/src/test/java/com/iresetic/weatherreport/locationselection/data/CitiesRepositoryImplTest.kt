package com.iresetic.weatherreport.locationselection.data

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import com.iresetic.weatherreport.core.constants.LOCATIONS_JSON_FILENAME
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@OptIn(ExperimentalCoroutinesApi::class)
class CitiesRepositoryImplTest {

    private lateinit var moshi: Moshi
    private lateinit var mockContext: Context
    private lateinit var mockAssetManager: AssetManager
    private lateinit var mockInputStream: InputStream
    private lateinit var mockInputStreamReader: InputStreamReader
    private lateinit var mockBufferReader: BufferedReader
    private lateinit var citiesRepositoryImpl: CitiesRepositoryImpl

    @Before
    fun setUp() {
        mockContext = mock()
        mockAssetManager = mock()
        moshi = Moshi.Builder().build()
        mockInputStream = mock()
        mockInputStreamReader = mock()
        mockBufferReader = mock()
        citiesRepositoryImpl = CitiesRepositoryImpl(mockContext, moshi)

        whenever(mockContext.assets).thenReturn(mockAssetManager)
        whenever(mockAssetManager.open(LOCATIONS_JSON_FILENAME)).thenReturn(mockInputStream)
        whenever(mockInputStream.bufferedReader())
    }


    @Test
    fun getAllCities_successfullyGetAllCities() = runTest {
        val result = citiesRepositoryImpl.getAllCities()

        assert(result.isNotEmpty())
    }
}