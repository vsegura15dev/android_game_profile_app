package com.vsegura15dev.gamingprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsegura15dev.gamingprofile.di.DispatcherIO
import com.vsegura15dev.gamingprofile.domain.usecase.IncrementPoints
import com.vsegura15dev.gamingprofile.presentation.model.MedalUI
import com.vsegura15dev.gamingprofile.presentation.model.mapper.MedalUIMapper
import com.vsegura15dev.gamingprofile.presentation.state.DetailMedalUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailMedalViewModel @Inject constructor(
    private val incrementPoints: IncrementPoints,
    private val mapper: MedalUIMapper,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
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

    fun onIncrementPoints() =
        viewModelScope.launch {
            val currentState = internalState.value as? DetailMedalUIState.Success ?: return@launch
            internalState.emit(currentState.copy(isAddingPoints = true))
            withContext(ioDispatcher) {
                delay(3000L)

                val medalUpdated =
                    with(mapper) {
                        incrementPoints(currentState.medalUI.toDomain()).toPresentation()
                    }
                val isNewLvl = medalUpdated.level > currentState.medalUI.level


                medalUpdated.isMaxLevelReached
                internalState.emit(
                    currentState.copy(
                        isAddingPoints = false,
                        medalUI = medalUpdated,
                        showLvlUpDialog = isNewLvl,
                        showMaxLvlDialog = medalUpdated.isMaxLevelReached
                    )
                )
            }
        }

    fun onCloseLvlUpDialog() {
        viewModelScope.launch {
            val currentState = internalState.value as? DetailMedalUIState.Success ?: return@launch
            internalState.emit(currentState.copy(showLvlUpDialog = false))
        }
    }

    fun onCloseMaxLvlReachedDialog() {
        viewModelScope.launch {
            val currentState = internalState.value as? DetailMedalUIState.Success ?: return@launch
            internalState.emit(currentState.copy(showMaxLvlDialog = false))
        }
    }
}