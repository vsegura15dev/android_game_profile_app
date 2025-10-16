package com.vsegura15dev.gamingprofile.data.repository

import app.cash.turbine.test
import com.vsegura15dev.gamingprofile.data.datasource.MedalDataSource
import com.vsegura15dev.gamingprofile.data.dto.MedalDTO
import com.vsegura15dev.gamingprofile.domain.model.Medal
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MedalRepositoryImplTest {

    @RelaxedMockK
    private lateinit var localDataSource: MedalDataSource

    @InjectMockKs
    private lateinit var repository: MedalRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given medals in data source when getMedals is called then return mapped domain medals`() =
        runTest {
            val medalDTO1 = createMedalDTO(id = "1", name = "First Medal")
            val medalDTO2 = createMedalDTO(id = "2", name = "Second Medal")
            val medalsDTO = listOf(medalDTO1, medalDTO2)

            every { localDataSource.getMedals() } returns flowOf(medalsDTO)

            repository.getMedals().test {
                val result = awaitItem()

                assertEquals(2, result.size)
                assertEquals("1", result[0].id)
                assertEquals("First Medal", result[0].name)
                assertEquals("2", result[1].id)
                assertEquals("Second Medal", result[1].name)
                awaitComplete()
            }
        }

    @Test
    fun `given empty list in data source when getMedals is called then return empty list`() =
        runTest {
            every { localDataSource.getMedals() } returns flowOf(emptyList())

            repository.getMedals().test {
                val result = awaitItem()

                assertEquals(0, result.size)
                awaitComplete()
            }
        }

    @Test
    fun `given null list in data source when getMedals is called then filter out null values`() =
        runTest {
            every { localDataSource.getMedals() } returns flowOf(null)

            repository.getMedals().test {
                awaitComplete()
            }
        }

    @Test
    fun `given multiple emissions when getMedals is called then return all mapped emissions`() =
        runTest {
            val medalDTO1 = createMedalDTO(id = "1", name = "First Medal")
            val medalDTO2 = createMedalDTO(id = "2", name = "Second Medal")
            val firstEmission = listOf(medalDTO1)
            val secondEmission = listOf(medalDTO1, medalDTO2)

            every { localDataSource.getMedals() } returns flowOf(firstEmission, secondEmission)

            repository.getMedals().test {
                val firstResult = awaitItem()
                val secondResult = awaitItem()

                assertEquals(1, firstResult.size)
                assertEquals("1", firstResult[0].id)

                assertEquals(2, secondResult.size)
                assertEquals("2", secondResult[1].id)
                awaitComplete()
            }
        }

    @Test
    fun `given a medal when saveMedal is called then save medal DTO to data source`() = runTest {
        val medal = createMedal(id = "1", name = "Test Medal")
        coEvery { localDataSource.saveMedal(any()) } returns Unit

        repository.saveMedal(medal)

        coVerify(exactly = 1) {
            localDataSource.saveMedal(
                match { dto ->
                    dto.id == "1" && dto.name == "Test Medal"
                }
            )
        }
    }

    @Test
    fun `given a medal with all properties when saveMedal is called then all properties are correctly mapped`() =
        runTest {
            val medal = Medal(
                id = "medal-123",
                name = "Champion",
                description = "Win 100 games",
                icon = "trophy",
                category = "Achievement",
                rarity = "Legendary",
                backgroundColor = "#FFD700",
                progressColor = "#FF6347",
                level = 5,
                points = 500,
                maxLevel = 10,
                reward = "1000 coins",
                unlockedAt = "2025-10-16",
                nextLevelGoal = "Win 50 more games",
                isLocked = false,
                animationType = "bounce"
            )
            coEvery { localDataSource.saveMedal(any()) } returns Unit

            repository.saveMedal(medal)

            coVerify(exactly = 1) {
                localDataSource.saveMedal(
                    match { dto ->
                        dto.id == "medal-123" &&
                                dto.name == "Champion" &&
                                dto.description == "Win 100 games" &&
                                dto.icon == "trophy" &&
                                dto.category == "Achievement" &&
                                dto.rarity == "Legendary" &&
                                dto.backgroundColor == "#FFD700" &&
                                dto.progressColor == "#FF6347" &&
                                dto.level == 5 &&
                                dto.points == 500 &&
                                dto.maxLevel == 10 &&
                                dto.reward == "1000 coins" &&
                                dto.unlockedAt == "2025-10-16" &&
                                dto.nextLevelGoal == "Win 50 more games" &&
                                !dto.isLocked &&
                                dto.animationType == "bounce"
                    }
                )
            }
        }

    @Test
    fun `given a list of medals when saveMedals is called then save all medals to data source`() =
        runTest {
            val medal1 = createMedal(id = "1", name = "First Medal")
            val medal2 = createMedal(id = "2", name = "Second Medal")
            val medals = listOf(medal1, medal2)
            coEvery { localDataSource.saveMedals(any()) } returns Unit

            repository.saveMedals(medals)

            coVerify(exactly = 1) {
                localDataSource.saveMedals(
                    match { dtos ->
                        dtos.size == 2 &&
                                dtos[0].id == "1" &&
                                dtos[0].name == "First Medal" &&
                                dtos[1].id == "2" &&
                                dtos[1].name == "Second Medal"
                    }
                )
            }
        }

    @Test
    fun `given an empty list when saveMedals is called then save empty list to data source`() =
        runTest {
            val medals = emptyList<Medal>()
            coEvery { localDataSource.saveMedals(any()) } returns Unit

            repository.saveMedals(medals)

            coVerify(exactly = 1) {
                localDataSource.saveMedals(match { it.isEmpty() })
            }
        }

    @Test
    fun `given a single medal in list when saveMedals is called then save single medal to data source`() =
        runTest {
            val medal = createMedal(id = "1", name = "Solo Medal")
            val medals = listOf(medal)
            coEvery { localDataSource.saveMedals(any()) } returns Unit

            repository.saveMedals(medals)

            coVerify(exactly = 1) {
                localDataSource.saveMedals(
                    match { dtos ->
                        dtos.size == 1 && dtos[0].id == "1"
                    }
                )
            }
        }

    @Test
    fun `given repository when resetMedals is called then delegate to data source`() = runTest {
        coEvery { localDataSource.resetMedals() } returns Unit

        repository.resetMedals()

        coVerify(exactly = 1) { localDataSource.resetMedals() }
    }


    private fun createMedalDTO(
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
    ) = MedalDTO(
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