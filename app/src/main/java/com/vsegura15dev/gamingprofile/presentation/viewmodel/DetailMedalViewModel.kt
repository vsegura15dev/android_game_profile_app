package com.vsegura15dev.gamingprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsegura15dev.gamingprofile.domain.usecase.IncrementPoints
import com.vsegura15dev.gamingprofile.presentation.model.MedalUI
import com.vsegura15dev.gamingprofile.presentation.model.mapper.toDomain
import com.vsegura15dev.gamingprofile.presentation.model.mapper.toPresentation
import com.vsegura15dev.gamingprofile.presentation.state.DetailMedalUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMedalViewModel @Inject constructor(
    private val incrementPoints: IncrementPoints
) : ViewModel() {

    private lateinit var medal: MedalUI
    private val internalState = MutableStateFlow<DetailMedalUIState>(DetailMedalUIState.Loading)
    val state = internalState.asStateFlow()

    fun init(medal: MedalUI) {
        this.medal = medal
        viewModelScope.launch {
            internalState.emit(
                DetailMedalUIState.Success(this@DetailMedalViewModel.medal)
            )
        }
    }

    fun onIncrementPoints() {
        viewModelScope.launch {
            val currentState = internalState.value as? DetailMedalUIState.Success ?: return@launch
            internalState.emit(currentState.copy(isAddingPoints = true))
            val medalUpdated = incrementPoints(currentState.medalUI.toDomain()).toPresentation()
            delay(3000L)
            internalState.emit(currentState.copy(isAddingPoints = false, medalUI = medalUpdated))
        }
    }
}