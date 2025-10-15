package com.vsegura15dev.gamingprofile.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vsegura15dev.gamingprofile.presentation.model.MedalUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedalsScreen(
    modifier: Modifier = Modifier,
    medals: List<MedalUI>,
    onMedalClick: (MedalUI) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("All Medals") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1C1C1E),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF000000)
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF000000)),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(medals) { medal ->
                MedalItem(
                    Modifier.clickable(
                        enabled = medal.isLocked.not(),
                        "",
                        null,
                        onClick = { onMedalClick(medal) }),
                    medal
                )
            }
        }
    }
}

@Composable
fun MedalItem(modifier: Modifier, medal: MedalUI) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(70.dp)
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Image(
                painter = painterResource(id = medal.icon),
                contentDescription = medal.name,
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        if (medal.isLocked.not()) Color.Transparent else Color(0xFF2C2C2E),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentScale = ContentScale.Fit
            )

            Surface(
                color = Color(0xFF673AB7),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(4.dp)
                    .size(20.dp),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "${medal.level}",
                        fontSize = 12.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = medal.name,
            color = if (medal.isLocked.not()) Color.White else Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
