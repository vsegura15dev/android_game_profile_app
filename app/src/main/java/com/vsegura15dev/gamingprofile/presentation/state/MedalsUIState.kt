package com.vsegura15dev.gamingprofile.presentation.state

import com.vsegura15dev.gamingprofile.presentation.model.MedalUI

sealed class MedalsUIState {
    data object Loading : MedalsUIState()
    data class Success(val medals: List<MedalUI>) : MedalsUIState()
}