package com.vsegura15dev.gamingprofile.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Build
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
import com.vsegura15dev.gamingprofile.presentation.screen.DetailMedalScreen
import com.vsegura15dev.gamingprofile.presentation.screen.LoadingScreen
import com.vsegura15dev.gamingprofile.presentation.state.DetailMedalUIState
import com.vsegura15dev.gamingprofile.presentation.theme.GamingProfileTheme
import com.vsegura15dev.gamingprofile.presentation.viewmodel.DetailMedalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMedalActivity : ComponentActivity() {

    private val viewModel: DetailMedalViewModel by viewModels<DetailMedalViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val medal = getMedal()
        viewModel.init(medal!!)
        enableEdgeToEdge()
        setContent {
            GamingProfileTheme {
                val state by viewModel.state.collectAsState()

                when (state) {
                    DetailMedalUIState.Loading -> LoadingScreen(Modifier.fillMaxSize())
                    is DetailMedalUIState.Success ->
                        DetailMedalScreen(
                            Modifier, (state as DetailMedalUIState.Success).medalUI
                        ) { viewModel.onIncrementPoints() }
                }
            }
        }
    }

    private fun getMedal() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getSerializableExtra(MEDAL_SELECTED, MedalUI::class.java)
    } else {
        intent.getSerializableExtra(MEDAL_SELECTED) as? MedalUI
    }

    companion object {

        @JvmStatic
        fun intentTo(context: Context, medal: MedalUI) = Intent(
            context, DetailMedalActivity::class.java
        ).apply {
            putExtra(MEDAL_SELECTED, medal)
        }

        private const val MEDAL_SELECTED = "medalSelected"
    }
}
