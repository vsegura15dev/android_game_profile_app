package com.vsegura15dev.gamingprofile.domain.usecase

import com.vsegura15dev.gamingprofile.domain.MAX_RANDOM_VALUE
import com.vsegura15dev.gamingprofile.domain.exception.MaxLevelReachedException
import com.vsegura15dev.gamingprofile.domain.generator.RandomNumberGenerator
import com.vsegura15dev.gamingprofile.domain.model.Medal
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class IncrementPointTest {

    @RelaxedMockK
    private lateinit var numberGenerator: RandomNumberGenerator

    @InjectMockKs
    private lateinit var useCase: IncrementPoint

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given medal with lvl 5 when increment points without reach next lvl then return medal with points updated`() =
        runTest {
            val pointsToAdd = 20
            every { numberGenerator.generateRandomUntilMax(MAX_RANDOM_VALUE) } returns pointsToAdd


            val newMedal = useCase(medal = medal)

            assertEquals(medal.level, newMedal.level)
            assertEquals(medal.points.plus(pointsToAdd), newMedal.points)
        }


    @Test
    fun `given medal with lvl 5 when increment points to reach next lvl then return medal with points and lvl updated`() =
        runTest {
            val pointsToAdd = 55
            every { numberGenerator.generateRandomUntilMax(MAX_RANDOM_VALUE) } returns pointsToAdd


            val newMedal = useCase(medal = medal)

            assertEquals(medal.level.plus(1), newMedal.level)
            assertEquals(0, newMedal.points)
        }

    @Test(expected = MaxLevelReachedException::class)
    fun `given medal with lvl max level and reached max point when increment points to reach next lvl then throws a reached lvl exception`() =
        runTest {

            val pointsToAdd = 55
            every { numberGenerator.generateRandomUntilMax(MAX_RANDOM_VALUE) } returns pointsToAdd

            useCase(
                medal = medal.copy(
                    level = medal.level,
                    isLocked = true,
                    points = MAX_RANDOM_VALUE
                )
            )
        }

    @Test
    fun `given medal with lvl max level when increment points to reach next lvl then returns locked medal`() =
        runTest {

            val pointsToAdd = 55
            every { numberGenerator.generateRandomUntilMax(MAX_RANDOM_VALUE) } returns pointsToAdd

            val result = useCase(
                medal = medal.copy(
                    level = medal.maxLevel
                )
            )

            assertTrue(result.isLocked)
            assertEquals(medal.maxLevel, result.level)
            assertEquals(MAX_RANDOM_VALUE, result.points)
        }


    private val medal = Medal(
        id = "m1",
        name = "Apostador Novato",
        description = "Alcanza tus primeros 100 puntos en cualquier modalidad.",
        icon = "medal_novato.png",
        category = "Progreso",
        rarity = "Common",
        backgroundColor = "#E6F4FF",
        progressColor = "#2196F3",
        level = 1,
        points = 45,
        maxLevel = 5,
        reward = "10 monedas",
        unlockedAt = "Registro completado",
        nextLevelGoal = "Suma 55 puntos más para alcanzar el siguiente nivel.",
        isLocked = false,
        animationType = "sparkle"
    )

}