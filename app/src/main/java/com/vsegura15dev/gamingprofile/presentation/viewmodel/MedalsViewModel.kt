package com.vsegura15dev.gamingprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsegura15dev.gamingprofile.domain.usecase.GetMedals
import com.vsegura15dev.gamingprofile.presentation.model.mapper.toPresentation
import com.vsegura15dev.gamingprofile.presentation.state.MedalsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MedalsViewModel @Inject constructor(
    private val getMedals: GetMedals,
) : ViewModel() {

    val state: StateFlow<MedalsUIState> = getMedals().map {
        MedalsUIState.Success(it.map { medal -> medal.toPresentation() })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = MedalsUIState.Loading
    )
}