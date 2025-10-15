package com.vsegura15dev.gamingprofile.presentation.model

import androidx.compose.ui.graphics.Color

data class MedalUI(
    val id: String,
    val name: String,
    val description: String,
    val icon: Int,
    val category: String,
    val rarity: String,
    val backgroundColor: Color,
    val progressColor: Color,
    val level: Int,
    val points: Int,
    val maxLevel: Int,
    val reward: String,
    val unlockedAt: String,
    val nextLevelGoal: String,
    val isLocked: Boolean,
    val animationType: String
)