package com.vsegura15dev.gamingprofile.presentation.screen.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MedalLevel(
    modifier: Modifier = Modifier,
    level: Int,
    progress: Float,
    @DrawableRes imageId: Int,
    progressColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(140.dp)
            .background(Color.Transparent)
    ) {
        CircularProgressIndicator(
            progress = { progress },
            trackColor = progressColor.copy(0.1f),
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            strokeWidth = 6.dp
        )

        Text(
            text = "$level",
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black
        )

        Image(
            painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.TopEnd)
                .offset(x = (4).dp, y = 6.dp)
                .background(
                    Color(0xFF2C2C2E),
                    shape = MaterialTheme.shapes.medium
                ),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberCircleWithIconPreview() {
    MaterialTheme {
        Surface(color = Color(0xFFF8F9FA)) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MedalLevel(
                    level = 42,
                    progress = 0.7f,
                    progressColor = Color.Black,
                    imageId = android.R.drawable.ic_input_add // Ícono de ejemplo
                )
            }
        }
    }
}