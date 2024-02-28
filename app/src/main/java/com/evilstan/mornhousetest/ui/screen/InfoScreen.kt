package com.evilstan.mornhousetest.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evilstan.mornhousetest.data.model.NumberInfo
import com.evilstan.mornhousetest.ui.theme.MornhouseTestTheme
import com.evilstan.mornhousetest.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun InfoScreen(state: StateFlow<UiState>) {
    val uiState by state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = uiState.numberInfo.number, style = MaterialTheme.typography.displayLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = uiState.numberInfo.description)
    }
}

@Preview(apiLevel = 33)
@Composable
private fun InfoScreenPreview() {
    val numberInfo = NumberInfo("15648189618", "Some very long test number with long description")

    MornhouseTestTheme {
        Surface {
            InfoScreen(MutableStateFlow(UiState(numberInfo = numberInfo)).asStateFlow())
        }
    }
}