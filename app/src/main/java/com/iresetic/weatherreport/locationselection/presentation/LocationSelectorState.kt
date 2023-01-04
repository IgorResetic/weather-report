package com.iresetic.weatherreport.locationselection.presentation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import com.iresetic.weatherreport.ui.theme.BlackTransparent_50

class LocationSelectorState(
    val focusRequest: FocusRequester,
    val listState: LazyListState,
    val placeholderTextColor: MutableState<Color>
) {
    fun setTextColor(color: Color) {
        placeholderTextColor.value = color
    }
    
    fun isScrolling(): Boolean = listState.isScrollInProgress
}

@Composable
fun rememberLocationSelectorState(
    requester: FocusRequester = remember { FocusRequester() },
    listState: LazyListState = rememberLazyListState(),
    placeholderTextColor: MutableState<Color> = remember { mutableStateOf(BlackTransparent_50) },
) = remember(requester, listState, placeholderTextColor) {
    LocationSelectorState(requester, listState, placeholderTextColor)
}
