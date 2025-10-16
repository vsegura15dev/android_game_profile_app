package com.vsegura15dev.gamingprofile.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vsegura15dev.gamingprofile.presentation.model.MedalUI
import com.vsegura15dev.gamingprofile.presentation.screen.LoadingScreen
import com.vsegura15dev.gamingprofile.presentation.screen.MedalsScreen
import com.vsegura15dev.gamingprofile.presentation.state.MedalsUIState
import com.vsegura15dev.gamingprofile.presentation.theme.GamingProfileTheme
import com.vsegura15dev.gamingprofile.presentation.viewmodel.MedalsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MedalsViewModel by viewModels<MedalsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamingProfileTheme {
                val state by viewModel.state.collectAsState()

                when (state) {
                    MedalsUIState.Loading -> LoadingScreen(Modifier.fillMaxSize())
                    is MedalsUIState.Success ->
                        MedalsScreen(
                            modifier = Modifier.fillMaxSize(),
                            medals = (state as MedalsUIState.Success).medals,
                            onMedalClick = ::navigateToDetail,
                            onAvatarClick = { viewModel.onResetMedals() },
                            onBackClick = {}
                        )
                }
            }
        }
    }

    private fun navigateToDetail(medal: MedalUI) {
        startActivity(DetailMedalActivity.intentTo(this, medal))
    }
}