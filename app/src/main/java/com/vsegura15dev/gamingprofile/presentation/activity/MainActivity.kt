package com.vsegura15dev.gamingprofile.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vsegura15dev.gamingprofile.presentation.screen.LoadingScreen
import com.vsegura15dev.gamingprofile.presentation.screen.MedalsScreen
import com.vsegura15dev.gamingprofile.presentation.state.UIState
import com.vsegura15dev.gamingprofile.presentation.theme.GamingProfileTheme
import com.vsegura15dev.gamingprofile.presentation.viewmodel.MedalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MedalViewModel by viewModels<MedalViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamingProfileTheme {

                val state by viewModel.state.collectAsState()

                when (state) {
                    UIState.Loading -> LoadingScreen(Modifier.fillMaxSize())
                    is UIState.Success ->
                        MedalsScreen(
                            modifier = Modifier.fillMaxSize(),
                            medals = (state as UIState.Success).medals
                        )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GamingProfileTheme {
        Greeting("Android")
    }
}