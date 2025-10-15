package com.vsegura15dev.gamingprofile.domain.usecase

import com.vsegura15dev.gamingprofile.domain.MAX_RANDOM_VALUE
import com.vsegura15dev.gamingprofile.domain.MIN_POINTS
import com.vsegura15dev.gamingprofile.domain.NEXT_LVL_REACH
import com.vsegura15dev.gamingprofile.domain.exception.MaxLevelReachedException
import com.vsegura15dev.gamingprofile.domain.generator.RandomNumberGenerator
import com.vsegura15dev.gamingprofile.domain.model.Medal

class IncrementPoint constructor(
    private val randomNumberGenerator: RandomNumberGenerator
) {

    suspend operator fun invoke(medal: Medal): Medal {
        if (medal.isLocked) throw MaxLevelReachedException()

        val newPoints =
            medal.points + randomNumberGenerator.generateRandomUntilMax(MAX_RANDOM_VALUE)
        return when {
            medal.level == medal.maxLevel -> {
                medal.copy(
                    points = if (newPoints > NEXT_LVL_REACH) NEXT_LVL_REACH else newPoints,
                    isLocked = true
                )
            }

            newPoints >= NEXT_LVL_REACH -> {
                medal.copy(
                    points = MIN_POINTS,
                    level = medal.level.plus(1)
                )
            }

            else -> medal.copy(
                points = newPoints
            )
        }
    }
}