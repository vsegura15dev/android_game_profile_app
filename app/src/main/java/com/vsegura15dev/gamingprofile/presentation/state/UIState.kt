package com.vsegura15dev.gamingprofile.presentation.state

import com.vsegura15dev.gamingprofile.presentation.model.MedalUI

sealed class UIState {
    data object Loading : UIState()
    data class Success(val medals: List<MedalUI>) : UIState()
}