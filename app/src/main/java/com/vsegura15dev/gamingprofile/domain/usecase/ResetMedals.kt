package com.vsegura15dev.gamingprofile.domain.usecase

import com.vsegura15dev.gamingprofile.domain.repository.MedalRepository
import javax.inject.Inject

class ResetMedals @Inject constructor(
    private val repository: MedalRepository
) {

    suspend operator fun invoke() {
        repository.resetMedals()
    }
}