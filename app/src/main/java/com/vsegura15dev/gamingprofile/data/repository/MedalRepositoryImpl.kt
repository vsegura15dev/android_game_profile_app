package com.vsegura15dev.gamingprofile.data.repository

import com.vsegura15dev.gamingprofile.data.datasource.MedalDataSource
import com.vsegura15dev.gamingprofile.data.dto.mapper.toDTO
import com.vsegura15dev.gamingprofile.data.dto.mapper.toDomain
import com.vsegura15dev.gamingprofile.data.reader.JsonReader
import com.vsegura15dev.gamingprofile.di.LocalDataSource
import com.vsegura15dev.gamingprofile.domain.model.Medal
import com.vsegura15dev.gamingprofile.domain.repository.MedalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class MedalRepositoryImpl @Inject constructor(
    @LocalDataSource private val localDataSource: MedalDataSource,
) : MedalRepository {

    override fun getMedals(): Flow<List<Medal>> {
        return localDataSource.getMedals().mapNotNull {
            it?.map { medal -> medal.toDomain() }
        }
    }

    override suspend fun saveMedal(medal: Medal) {
        localDataSource.saveMedal(medal.toDTO())
    }

    override suspend fun saveMedals(medals: List<Medal>) {
        localDataSource.saveMedals(medals.map { it.toDTO() })
    }
}