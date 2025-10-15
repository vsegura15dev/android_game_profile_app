package com.vsegura15dev.gamingprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsegura15dev.gamingprofile.domain.usecase.GetMedals
import com.vsegura15dev.gamingprofile.domain.usecase.IncrementPoints
import com.vsegura15dev.gamingprofile.presentation.model.mapper.toPresentation
import com.vsegura15dev.gamingprofile.presentation.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedalViewModel @Inject constructor(
    private val getMedals: GetMedals,
    private val incrementPoints: IncrementPoints
) : ViewModel() {

    val state: StateFlow<UIState> = getMedals().map {
        UIState.Success(it.map { medal -> medal.toPresentation() })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = UIState.Loading
    )
}