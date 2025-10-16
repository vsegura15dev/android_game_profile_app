package com.vsegura15dev.gamingprofile.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import com.vsegura15dev.gamingprofile.domain.model.Medal
import com.vsegura15dev.gamingprofile.domain.usecase.GetMedals
import com.vsegura15dev.gamingprofile.domain.usecase.ResetMedals
import com.vsegura15dev.gamingprofile.presentation.model.MedalUI
import com.vsegura15dev.gamingprofile.presentation.model.mapper.MedalUIMapper
import com.vsegura15dev.gamingprofile.presentation.state.MedalsUIState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MedalsViewModelTest {

    @MockK
    private lateinit var getMedals: GetMedals

    @MockK
    private lateinit var resetMedals: ResetMedals

    @MockK
    private lateinit var mapper: MedalUIMapper

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: MedalsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        
        every { 
            with(mapper) { any<Medal>().toPresentation() }
        } answers {
            val medal = firstArg<Medal>()
            getMedalUI().copy(
                id = medal.id,
                name = medal.name,
                description = medal.description,
                iconName = medal.icon,
                category = medal.category,
                rarity = medal.rarity,
                backgroundColorHex = medal.backgroundColor,
                progressColorHex = medal.progressColor,
                level = medal.level,
                points = medal.points,
                maxLevel = medal.maxLevel,
                reward = medal.reward,
                unlockedAt = medal.unlockedAt,
                nextLevelGoal = medal.nextLevelGoal,
                isLocked = medal.isLocked,
                animationType = medal.animationType
            )
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given viewmodel initialized when state is observed then initial state is loading`() = runTest {
        val medals = listOf(getMedal().copy(id = "1", name = "Test Medal"))
        every { getMedals() } returns flowOf(medals)

        viewModel = MedalsViewModel(getMedals, resetMedals, mapper, testDispatcher)

        viewModel.state.test {
            val initialState = awaitItem()

            assertTrue(initialState is MedalsUIState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given medals available when viewmodel is initialized then state emits success with medals`() = runTest {
        val medal1 = getMedal().copy(id = "1", name = "First Medal")
        val medal2 = getMedal().copy(id = "2", name = "Second Medal")
        val medals = listOf(medal1, medal2)
        every { getMedals() } returns flowOf(medals)

        viewModel = MedalsViewModel(getMedals, resetMedals, mapper, testDispatcher)

        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState is MedalsUIState.Loading)

            advanceTimeBy(3000L)

            val successState = awaitItem()
            assertTrue(successState is MedalsUIState.Success)
            assertEquals(2, (successState as MedalsUIState.Success).medals.size)
            assertEquals("1", successState.medals[0].id)
            assertEquals("First Medal", successState.medals[0].name)
            assertEquals("2", successState.medals[1].id)
            assertEquals("Second Medal", successState.medals[1].name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given empty medals list when viewmodel is initialized then state emits success with empty list`() = runTest {
        every { getMedals() } returns flowOf(emptyList())

        viewModel = MedalsViewModel(getMedals, resetMedals, mapper, testDispatcher)

        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState is MedalsUIState.Loading)

            advanceTimeBy(3000L)

            val successState = awaitItem()
            assertTrue(successState is MedalsUIState.Success)
            assertEquals(0, (successState as MedalsUIState.Success).medals.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given multiple medal emissions when viewmodel is initialized then state updates with latest medals`() = runTest {
        val medal1 = getMedal().copy(id = "1", name = "First Medal")
        val medal2 = getMedal().copy(id = "2", name = "Second Medal")
        val firstEmission = listOf(medal1)
        val secondEmission = listOf(medal1, medal2)
        every { getMedals() } returns flowOf(firstEmission, secondEmission)

        viewModel = MedalsViewModel(getMedals, resetMedals, mapper, testDispatcher)

        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState is MedalsUIState.Loading)

            advanceTimeBy(3000L)

            val firstSuccessState = awaitItem()
            assertTrue(firstSuccessState is MedalsUIState.Success)
            assertEquals(1, (firstSuccessState as MedalsUIState.Success).medals.size)

            val secondSuccessState = awaitItem()
            assertTrue(secondSuccessState is MedalsUIState.Success)
            assertEquals(2, (secondSuccessState as MedalsUIState.Success).medals.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given viewmodel when onResetMedals is called then resetMedals use case is invoked`() = runTest {
        val medals = listOf(getMedal().copy(id = "1", name = "Test Medal"))
        every { getMedals() } returns flowOf(medals)
        coEvery { resetMedals() } returns Unit

        viewModel = MedalsViewModel(getMedals, resetMedals, mapper, testDispatcher)

        viewModel.onResetMedals()
        advanceUntilIdle()

        coVerify(exactly = 1) { resetMedals() }
    }

    @Test
    fun `given viewmodel when onResetMedals is called multiple times then resetMedals use case is invoked multiple times`() = runTest {
        val medals = listOf(getMedal().copy(id = "1", name = "Test Medal"))
        every { getMedals() } returns flowOf(medals)
        coEvery { resetMedals() } returns Unit

        viewModel = MedalsViewModel(getMedals, resetMedals, mapper, testDispatcher)

        viewModel.onResetMedals()
        viewModel.onResetMedals()
        viewModel.onResetMedals()
        advanceUntilIdle()

        coVerify(exactly = 3) { resetMedals() }
    }

    private fun getMedal() = Medal(
        id = "test-id",
        name = "Test Medal",
        description = "Test Description",
        icon = "test_icon",
        category = "Test Category",
        rarity = "Common",
        backgroundColor = "#FFFFFF",
        progressColor = "#000000",
        level = 1,
        points = 100,
        maxLevel = 5,
        reward = "Test Reward",
        unlockedAt = "2025-01-01",
        nextLevelGoal = "Next Goal",
        isLocked = true,
        animationType = "fade"
    )

    private fun getMedalUI() = MedalUI(
        id = "test-id",
        name = "Test Medal",
        description = "Test Description",
        icon = 0,
        iconName = "test_icon",
        category = "Test Category",
        rarity = "Common",
        backgroundColor = Color.White,
        backgroundColorHex = "#FFFFFF",
        progressColor = Color.Black,
        progressColorHex = "#000000",
        level = 1,
        points = 100,
        maxLevel = 5,
        reward = "Test Reward",
        unlockedAt = "2025-01-01",
        nextLevelGoal = "Next Goal",
        isLocked = true,
        animationType = "fade"
    )
}
