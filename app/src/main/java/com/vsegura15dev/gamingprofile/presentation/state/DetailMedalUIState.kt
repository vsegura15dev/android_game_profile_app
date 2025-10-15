package com.vsegura15dev.gamingprofile.presentation.state

import com.vsegura15dev.gamingprofile.presentation.model.MedalUI

sealed class DetailMedalUIState {
    data object Loading : DetailMedalUIState()
    data class Success(val medalUI: MedalUI, val isAddingPoints: Boolean = false) :
        DetailMedalUIState()
}