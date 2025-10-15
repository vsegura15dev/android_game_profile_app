package com.vsegura15dev.gamingprofile.presentation.model.mapper

import com.vsegura15dev.gamingprofile.domain.model.Medal
import com.vsegura15dev.gamingprofile.presentation.model.MedalUI
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.vsegura15dev.gamingprofile.R

fun Medal.toPresentation() = MedalUI(
    id = id,
    name = name,
    description = description,
    icon = icon.toDrawable(),
    iconName = "",
    category = category,
    rarity = rarity,
    backgroundColor = Color(backgroundColor.toColorInt()),
    backgroundColorHex = backgroundColor,
    progressColor = Color(progressColor.toColorInt()),
    progressColorHex = progressColor,
    level = level,
    points = points,
    maxLevel = maxLevel,
    reward = reward,
    unlockedAt = unlockedAt,
    nextLevelGoal = nextLevelGoal,
    isLocked = isLocked,
    animationType = animationType
)

fun MedalUI.toDomain() = Medal(
    id = id,
    name = name,
    description = description,
    icon = iconName,
    category = category,
    rarity = rarity,
    backgroundColor = backgroundColorHex,
    progressColor = progressColorHex,
    level = level,
    points = points,
    maxLevel = maxLevel,
    reward = reward,
    unlockedAt = unlockedAt,
    nextLevelGoal = nextLevelGoal,
    isLocked = isLocked,
    animationType = animationType
)

private val medalResources = mapOf(
    "medal_novato" to R.drawable.medal_novato,
    "medal_album" to R.drawable.medal_album,
    "medal_cazafijas" to R.drawable.medal_cazafijas,
    "medal_capo" to R.drawable.medal_capo,
    "medal_constante" to R.drawable.medal_constante,
    "medal_estratega" to R.drawable.medal_estratega,
    "medal_leyenda" to R.drawable.medal_leyenda,
    "medal_mision" to R.drawable.medal_mision,
    "medal_racha" to R.drawable.medal_racha,
    "medal_rey" to R.drawable.medal_rey,
)

private fun String.toDrawable() = medalResources[this] ?: R.drawable.medal_novato
