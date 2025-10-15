package com.vsegura15dev.gamingprofile.data.dto

import kotlinx.serialization.Serializable

@OptIn(kotlinx.serialization.InternalSerializationApi::class)
@Serializable
data class MedalDTO(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val category: String,
    val rarity: String,
    val backgroundColor: String,
    val progressColor: String,
    val level: Int,
    val points: Int,
    val maxLevel: Int,
    val reward: String,
    val unlockedAt: String,
    val nextLevelGoal: String,
    val isLocked: Boolean,
    val animationType: String
)