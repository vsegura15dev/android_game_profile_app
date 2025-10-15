package com.vsegura15dev.gamingprofile.data.dto.mapper

import com.vsegura15dev.gamingprofile.data.dto.MedalDTO
import com.vsegura15dev.gamingprofile.domain.model.Medal

internal fun MedalDTO.toDomain() =
    Medal(
        id = id,
        name = name,
        description = description,
        icon = icon,
        category = category,
        rarity = rarity,
        backgroundColor = backgroundColor,
        progressColor = progressColor,
        level = level,
        points = points,
        maxLevel = maxLevel,
        reward = reward,
        unlockedAt = unlockedAt,
        nextLevelGoal = nextLevelGoal,
        isLocked = isLocked,
        animationType = animationType
    )

internal fun Medal.toDTO() = MedalDTO(
    id = id,
    name = name,
    description = description,
    icon = icon,
    category = category,
    rarity = rarity,
    backgroundColor = backgroundColor,
    progressColor = progressColor,
    level = level,
    points = points,
    maxLevel = maxLevel,
    reward = reward,
    unlockedAt = unlockedAt,
    nextLevelGoal = nextLevelGoal,
    isLocked = isLocked,
    animationType = animationType
)