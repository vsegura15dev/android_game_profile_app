package com.vsegura15dev.gamingprofile.domain.usecase

import app.cash.turbine.test
import com.vsegura15dev.gamingprofile.domain.model.Medal
import com.vsegura15dev.gamingprofile.domain.repository.MedalRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMedalsTest {

    @RelaxedMockK
    private lateinit var repository: MedalRepository

    @InjectMockKs
    private lateinit var getMedals: GetMedals

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given medals in repository when invoke is called then return medals flow`() = runTest {
        val medal1 = createMedal(id = "1", name = "First Medal")
        val medal2 = createMedal(id = "2", name = "Second Medal")
        val medals = listOf(medal1, medal2)

        every { repository.getMedals() } returns flowOf(medals)

        getMedals().test {
            val result = awaitItem()

            assertEquals(2, result.size)
            assertEquals("1", result[0].id)
            assertEquals("First Medal", result[0].name)
            assertEquals("2", result[1].id)
            assertEquals("Second Medal", result[1].name)
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMedals() }
    }

    @Test
    fun `given empty list in repository when invoke is called then return empty flow`() = runTest {
        every { repository.getMedals() } returns flowOf(emptyList())

        getMedals().test {
            val result = awaitItem()

            assertEquals(0, result.size)
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMedals() }
    }

    @Test
    fun `given multiple emissions when invoke is called then return all emissions`() = runTest {
        val medal1 = createMedal(id = "1", name = "First Medal")
        val medal2 = createMedal(id = "2", name = "Second Medal")
        val firstEmission = listOf(medal1)
        val secondEmission = listOf(medal1, medal2)

        every { repository.getMedals() } returns flowOf(firstEmission, secondEmission)

        getMedals().test {
            val firstResult = awaitItem()
            val secondResult = awaitItem()

            assertEquals(1, firstResult.size)
            assertEquals("1", firstResult[0].id)

            assertEquals(2, secondResult.size)
            assertEquals("2", secondResult[1].id)
            awaitComplete()
        }

        verify(exactly = 1) { repository.getMedals() }
    }

    private fun createMedal(
        id: String = "test-id",
        name: String = "Test Medal",
        description: String = "Test Description",
        icon: String = "test_icon",
        category: String = "Test Category",
        rarity: String = "Common",
        backgroundColor: String = "#FFFFFF",
        progressColor: String = "#000000",
        level: Int = 1,
        points: Int = 100,
        maxLevel: Int = 5,
        reward: String = "Test Reward",
        unlockedAt: String = "2025-01-01",
        nextLevelGoal: String = "Next Goal",
        isLocked: Boolean = true,
        animationType: String = "fade"
    ) = Medal(
        id = id,
        name = name,
        description = description,
        icon = icon,
        category = category,
        rarity = rarity,
        backgroundColor = backgroundColor,
        progressColor = progressColor,
        level = level,
        points = points,
        maxLevel = maxLevel,
        reward = reward,
        unlockedAt = unlockedAt,
        nextLevelGoal = nextLevelGoal,
        isLocked = isLocked,
        animationType = animationType
    )
}
