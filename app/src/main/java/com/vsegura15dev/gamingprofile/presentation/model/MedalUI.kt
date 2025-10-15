package com.vsegura15dev.gamingprofile.presentation.model

import androidx.compose.ui.graphics.Color
import java.io.Serializable

data class MedalUI(
    val id: String,
    val name: String,
    val description: String,
    val icon: Int,
    val iconName: String,
    val category: String,
    val rarity: String,
    val backgroundColor: Color,
    val backgroundColorHex: String,
    val progressColor: Color,
    val progressColorHex: String,
    val level: Int,
    val points: Int,
    val maxLevel: Int,
    val reward: String,
    val unlockedAt: String,
    val nextLevelGoal: String,
    val isLocked: Boolean,
    val animationType: String
) : Serializable