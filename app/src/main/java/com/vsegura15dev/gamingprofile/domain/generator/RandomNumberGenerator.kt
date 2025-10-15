package com.vsegura15dev.gamingprofile.domain.generator

import com.vsegura15dev.gamingprofile.domain.MAX_RANDOM_VALUE
import kotlin.random.Random

interface RandomNumberGenerator {
    fun generateRandomUntilMax(max: Int): Int
}

internal class RandomNumberGeneratorImpl : RandomNumberGenerator {

    override fun generateRandomUntilMax(max: Int) = Random.nextInt(MAX_RANDOM_VALUE)

}