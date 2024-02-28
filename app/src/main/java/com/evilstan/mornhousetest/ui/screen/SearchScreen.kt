package com.evilstan.mornhousetest.ui.screen

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evilstan.mornhousetest.R
import com.evilstan.mornhousetest.data.model.NumberInfo
import com.evilstan.mornhousetest.ui.theme.MornhouseTestTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun SearchScreen(
    isLoading: Boolean,
    historyFlow: StateFlow<List<NumberInfo>>,
    search: (String?) -> Unit
) {
    val history by historyFlow.collectAsStateWithLifecycle()
    var searchString by rememberSaveable { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchString,
            onValueChange = {
                // on some devices, the '.' and '-' are on the same key
                val newInput = it.replace(".", "-")
                if (it.length > 50) return@TextField

                searchString = if (searchString == "0" && newInput.isNotEmpty())
                    newInput.last().toString()
                else newInput
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onSearch = { search(searchString) }),
            placeholder = { Text(text = stringResource(R.string.hint)) },
            maxLines = 2
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp),
            onClick = { search(searchString) },
            enabled = searchString.isNotEmpty()
        ) {
            Text(text = stringResource(R.string.search))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp),
            onClick = { search(null) },
        ) {
            Text(text = stringResource(R.string.show_random))
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(history.size) { i ->
                HistoryItem(history[i]) { search(it) }
            }
        }
    }

    Preloader(isLoading)
}

@Composable
private fun HistoryItem(numberInfo: NumberInfo, onClick: (String) -> Unit) {
    Spacer(modifier = Modifier.height(8.dp))
    Card(
        Modifier
            .fillMaxWidth()
            .clickable { onClick(numberInfo.number) }) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = stringResource(R.string.number, numberInfo.number),
            fontSize = 18.sp
        )

        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = numberInfo.description,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun Preloader(visible: Boolean) {
    Box(Modifier.fillMaxSize()) {
        val alpha by animateFloatAsState(
            targetValue = if (visible) 0.5f else 0f,
            animationSpec = TweenSpec(), label = ""
        )
        val color = MaterialTheme.colorScheme.background
        Canvas(Modifier.fillMaxSize()) { drawRect(color = color, alpha = alpha) }

        if (visible) CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun SearchScreenPreview() {
    val testData = listOf(
        NumberInfo("123", "Number one two three"),
        NumberInfo("256", "Number two five six"),
        NumberInfo("15648189618", "Some very long test number with long description"),
    )

    MornhouseTestTheme {
        Surface {
            SearchScreen(
                isLoading = false,
                historyFlow = MutableStateFlow(testData).asStateFlow()
            ) {}
        }
    }
}