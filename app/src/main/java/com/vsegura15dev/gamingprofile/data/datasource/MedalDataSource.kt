package com.vsegura15dev.gamingprofile.data.datasource

import com.vsegura15dev.gamingprofile.data.dto.MedalDTO
import kotlinx.coroutines.flow.Flow

interface MedalDataSource {

    fun getMedals(): Flow<List<MedalDTO>?>

    suspend fun saveMedal(medal: MedalDTO)

    suspend fun saveMedals(medals: List<MedalDTO>)
}