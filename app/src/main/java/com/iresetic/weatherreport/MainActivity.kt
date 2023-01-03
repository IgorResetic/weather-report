package com.iresetic.weatherreport

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.locationselection.domain.usecases.GetAllCities
import com.iresetic.weatherreport.locationselection.domain.usecases.GetCityData
import com.iresetic.weatherreport.ui.theme.WeatherReportTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var getAllCities: GetAllCities
    @Inject lateinit var getCityData: GetCityData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            withContext(Dispatchers.IO) {
                Log.d("TEST_CITY", "${getAllCities.invoke().size}")
                Log.d("TEST_CITY", "City: ${getCityData.invoke("3041563")}")
            }
        }

        setContent {
            WeatherReportTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherReportTheme {
        Greeting("Android")
    }
}