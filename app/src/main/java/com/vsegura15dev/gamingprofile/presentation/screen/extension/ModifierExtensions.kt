package com.vsegura15dev.gamingprofile.presentation.screen.extension

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
internal fun Modifier.clickableWithLimit(
    limit: Int,
    timeToReset: Long = 3000L,
    onClickable: () -> Unit = {}
): Modifier {
    var attempts by remember { mutableIntStateOf(0) }

    LaunchedEffect(attempts > 0) {
        if (attempts > 0) {
            delay(timeToReset)
            attempts = 0
        }
    }

    return this.clickable(enabled = true) {
        if (attempts < limit) {
            attempts++
        } else {
            onClickable()
            attempts = 0
        }
    }
}