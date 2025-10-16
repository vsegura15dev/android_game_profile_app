package com.vsegura15dev.gamingprofile.data.repository

import com.vsegura15dev.gamingprofile.data.datasource.MedalDataSource
import com.vsegura15dev.gamingprofile.data.dto.mapper.mapToDomain
import com.vsegura15dev.gamingprofile.data.dto.mapper.toDTO
import com.vsegura15dev.gamingprofile.di.LocalDataSource
import com.vsegura15dev.gamingprofile.domain.model.Medal
import com.vsegura15dev.gamingprofile.domain.repository.MedalRepository
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class MedalRepositoryImpl @Inject constructor(
    @LocalDataSource private val localDataSource: MedalDataSource,
) : MedalRepository {

    override fun getMedals() =
        localDataSource.getMedals().mapNotNull {
            it?.mapToDomain()
        }

    override suspend fun saveMedal(medal: Medal) {
       localDataSource.saveMedal(medal.toDTO())
    }

    override suspend fun saveMedals(medals: List<Medal>) {
        localDataSource.saveMedals(medals.map { it.toDTO() })
    }

    override suspend fun resetMedals() = localDataSource.resetMedals()
}