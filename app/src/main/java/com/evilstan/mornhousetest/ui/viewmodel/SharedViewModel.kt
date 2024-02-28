package com.evilstan.mornhousetest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evilstan.mornhousetest.domain.repository.MainRepository
import com.evilstan.mornhousetest.ui.state.SearchState
import com.evilstan.mornhousetest.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val historyFlow = repository.getHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    fun search(number: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(searchState = SearchState.Loading) }

            val numberInfo = if (number == null) repository.getRandomNumber()
            else repository.getNumber(number)

            if (numberInfo == null) _uiState.update { it.copy(searchState = SearchState.Error) }
            else _uiState.update {
                it.copy(
                    numberInfo = numberInfo,
                    searchState = SearchState.Success
                )
            }
        }
    }

    fun inactivate() {
        _uiState.update { it.copy(searchState = SearchState.Inactive) }
    }
}