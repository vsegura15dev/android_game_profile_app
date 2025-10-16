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
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vsegura15dev.gamingprofile.presentation.model.MedalUI
import com.vsegura15dev.gamingprofile.presentation.screen.DetailMedalScreen
import com.vsegura15dev.gamingprofile.presentation.screen.LoadingScreen
import com.vsegura15dev.gamingprofile.presentation.state.DetailMedalUIState
import com.vsegura15dev.gamingprofile.presentation.theme.GamingProfileTheme
import com.vsegura15dev.gamingprofile.presentation.viewmodel.DetailMedalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMedalActivity : ComponentActivity() {

    private val viewModel: DetailMedalViewModel by viewModels<DetailMedalViewModel>()

    private var executeJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMedal()?.let { viewModel.init(it) }

        enableEdgeToEdge()
        setContent {
            GamingProfileTheme {
                when (val state = viewModel.state.collectAsState().value) {
                    DetailMedalUIState.Loading -> LoadingScreen(Modifier.fillMaxSize())
                    is DetailMedalUIState.Success ->
                        DetailMedalScreen(
                            Modifier, state.medalUI,
                            showCounterRoll = state.isAddingPoints,
                            showLevelUpDialog = state.showLvlUpDialog,
                            onCloseLevelUpDialog = { viewModel.onCloseLvlUpDialog() },
                            onCloseMaxLevelReached = { viewModel.onCloseMaxLvlReachedDialog()}
                        ) {
                            lifecycleScope.launch {
                                repeatOnLifecycle(Lifecycle.State.STARTED) {
                                    executeJob = viewModel.onIncrementPoints()
                                    executeJob?.invokeOnCompletion {
                                        if (it == null) {
                                            this@launch.cancel()
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        executeJob?.cancel()
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
