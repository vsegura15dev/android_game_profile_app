package com.vsegura15dev.gamingprofile.presentation.screen.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun MedalPointProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color,
    label: String
) {

    Box(modifier) {
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        LinearProgressIndicator(
            modifier = Modifier
                .height(25.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            progress = { animatedProgress },
            color = progressColor,
            strokeCap = StrokeCap.Square,
            gapSize = 2.dp,
            trackColor = progressColor.copy(alpha = 0.2f)
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge,
            text = label, color = Color.White
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun MedalPointProgressPreview() {
    MaterialTheme {
        val progress = remember {
            mutableFloatStateOf(0.05f)
        }

        MedalPointProgress(
            Modifier.fillMaxWidth(),
            progress = progress.floatValue,
            progressColor = Color.Green,
            label = "5 puntos"
        )

        LaunchedEffect(Unit) {
            delay(3000L)
            progress.floatValue = 0.4f
            delay(3000L)
            progress.floatValue = 0.7f
            delay(3000L)
            progress.floatValue = 1f
        }
    }
}