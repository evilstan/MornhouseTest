package com.evilstan.mornhousetest.ui.state

import com.evilstan.mornhousetest.data.model.NumberInfo

data class UiState(
    val numberInfo: NumberInfo = NumberInfo(),
    var searchState: SearchState = SearchState.Inactive
) {
    fun isError() = searchState == SearchState.Error

    fun isSuccess() = searchState == SearchState.Success

    fun isLoading() = searchState == SearchState.Loading

    fun inactivate() {
        searchState = SearchState.Inactive
    }
}

sealed class SearchState {
    data object Success : SearchState()
    data object Loading : SearchState()
    data object Error : SearchState()
    data object Inactive : SearchState()
}
