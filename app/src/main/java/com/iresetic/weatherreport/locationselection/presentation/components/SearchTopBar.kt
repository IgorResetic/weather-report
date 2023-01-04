package com.iresetic.weatherreport.locationselection.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iresetic.weatherreport.R
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorEvent
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorEvent.ClearSearchBarText
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorState
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorViewModel
import com.iresetic.weatherreport.ui.theme.BlackTransparent_15
import com.iresetic.weatherreport.ui.theme.BlackTransparent_50

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchTopBar(navController: NavController, locationSelectorState: LocationSelectorState) {
    val viewModel = hiltViewModel<LocationSelectorViewModel>()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        TopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White,
            ),
            title = {
                OutlinedTextField(
                    value = viewModel.state.searchedCityValue,
                    onValueChange = { viewModel.onEvent(LocationSelectorEvent.SearchForCity(it)) },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                    }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(locationSelectorState.focusRequest)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                locationSelectorState.setTextColor(BlackTransparent_15)
                            } else {
                                locationSelectorState.setTextColor(BlackTransparent_50)
                            }
                        },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.location_selector_search_hint),
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = locationSelectorState.placeholderTextColor.value
                            )
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.Transparent,
                    ),
                    trailingIcon = {
                        if (viewModel.state.searchedCityValue.isNotEmpty()) {
                            Icon(Icons.Default.Clear,
                                contentDescription = "Clear text",
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(ClearSearchBarText)
                                    }
                            )
                        }
                    }
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    keyboardController?.hide()
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
        )
        Divider(color = BlackTransparent_15, thickness = 0.8.dp)
    }
}