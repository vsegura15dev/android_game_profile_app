package com.vsegura15dev.gamingprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsegura15dev.gamingprofile.di.DispatcherIO
import com.vsegura15dev.gamingprofile.domain.usecase.GetMedals
import com.vsegura15dev.gamingprofile.domain.usecase.ResetMedals
import com.vsegura15dev.gamingprofile.presentation.model.mapper.toPresentation
import com.vsegura15dev.gamingprofile.presentation.state.MedalsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedalsViewModel @Inject constructor(
    private val getMedals: GetMedals,
    private val resetMedals: ResetMedals,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val internalState = MutableStateFlow<MedalsUIState>(MedalsUIState.Loading)
    val state = internalState.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            delay(3000L)
            getMedals().collect {
                val medals = it.map { medal -> medal.toPresentation() }
                internalState.emit(MedalsUIState.Success(medals))
            }
        }
    }

    fun onResetMedals() {
        viewModelScope.launch(ioDispatcher) {
            resetMedals()
        }
    }
}