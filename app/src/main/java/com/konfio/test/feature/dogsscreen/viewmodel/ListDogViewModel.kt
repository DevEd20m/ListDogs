package com.konfio.test.feature.dogsscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.konfio.domain.usecase.GetDogsUseCase
import com.konfio.test.feature.dogsscreen.model.DogUi
import com.konfio.test.feature.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ListDogState {
    data class Success(val dogs: List<DogUi>) : ListDogState()
    data class Error(val message: String) : ListDogState()
    data object Empty : ListDogState()
    data object Loading : ListDogState()
}


sealed class ListDogEvent {
    companion object LoadDogs : ListDogEvent()
}

@HiltViewModel
class ListDogViewModel @Inject constructor(private val getDogsUseCase: GetDogsUseCase) : ViewModel() {
    private val _state = MutableStateFlow<ListDogState>(ListDogState.Empty)
    val state: StateFlow<ListDogState> = _state.asStateFlow()


    fun onEvent(event: ListDogEvent) {
        when (event) {
            is ListDogEvent.LoadDogs -> loadDogs()
        }
    }

    private fun loadDogs() = viewModelScope.launch {
        _state.value = ListDogState.Loading
        when (val result = getDogsUseCase()) {
            is Either.Right -> _state.value = ListDogState.Success(result.value.map { it.toUi() })
            is Either.Left -> _state.value = ListDogState.Error(result.value.toString())
            else -> {
                _state.value = ListDogState.Empty
            }
        }
    }


}