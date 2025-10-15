package com.vsegura15dev.gamingprofile.domain.repository

import com.vsegura15dev.gamingprofile.domain.model.Medal
import kotlinx.coroutines.flow.Flow

interface MedalRepository {
    fun getMedals(): Flow<List<Medal>>
    suspend fun saveMedal(medal: Medal)
    suspend fun saveMedals(medals: List<Medal>)
}