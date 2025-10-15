package com.vsegura15dev.gamingprofile.domain.usecase

import com.vsegura15dev.gamingprofile.domain.repository.MedalRepository
import javax.inject.Inject

class GetMedals @Inject constructor(
    private val repository: MedalRepository
) {
    operator fun invoke() = repository.getMedals()
}