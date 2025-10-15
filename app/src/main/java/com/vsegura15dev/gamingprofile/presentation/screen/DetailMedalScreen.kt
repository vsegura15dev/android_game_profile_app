package com.vsegura15dev.gamingprofile.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vsegura15dev.gamingprofile.R
import com.vsegura15dev.gamingprofile.presentation.model.MedalUI
import kotlinx.coroutines.delay

@Composable
fun DetailMedalScreen(
    modifier: Modifier = Modifier,
    medal: MedalUI,
    showCounterRoll: Boolean = false,
    onCTAClicked: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFF9FAFB), shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = medal.icon),
                    contentDescription = medal.name,
                    modifier = Modifier
                        .size(256.dp)
                        .background(
                            Color(0xFF2C2C2E),
                            shape = MaterialTheme.shapes.medium
                        ),
                    contentScale = ContentScale.Fit
                )
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 1.dp,
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        text = medal.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💭", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = medal.description,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    Row {
                        Text("🟢", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = medal.nextLevelGoal,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Text(modifier = Modifier.wrapContentSize(),
                text = "Puntos: ${medal.points}",
                style = MaterialTheme.typography.headlineMedium)

            Button(
                onClick = onCTAClicked,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    "Get your points!",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        if (showCounterRoll) {
            CounterRollDialog()
        }
    }
}

@Composable
private fun CounterRollDialog() {
    Dialog(
        onDismissRequest = {}, properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {

            val number = remember { mutableIntStateOf(0) }
            LaunchedEffect(Unit) {
                repeat(200) {
                    number.intValue = (1..100).random()
                    delay(50)
                }
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = number.intValue.toString(),
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailMedalScreenPreview() {
    MaterialTheme {
        DetailMedalScreen(
            showCounterRoll = false,
            medal = MedalUI(
                id = "m9",
                name = "Racha Imparable",
                description = "Mantén una racha de 7 días ganando.",
                icon = R.drawable.medal_racha,
                iconName = "",
                category = "Racha",
                rarity = "Epic",
                backgroundColor = Color(0xFFFCE4EC),
                backgroundColorHex = "",
                progressColor = Color(0xFFE91E63),
                progressColorHex = "",
                level = 1,
                points = 0,
                maxLevel = 10,
                reward = "50 monedas",
                unlockedAt = "Racha de 3 días",
                nextLevelGoal = "Suma 100 puntos más para alcanzar el siguiente nivel.",
                isLocked = false,
                animationType = ""
            )
        )
    }
}