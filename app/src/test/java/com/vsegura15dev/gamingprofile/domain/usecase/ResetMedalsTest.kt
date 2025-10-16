package com.vsegura15dev.gamingprofile.domain.usecase

import com.vsegura15dev.gamingprofile.domain.repository.MedalRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ResetMedalsTest {

    @RelaxedMockK
    private lateinit var repository: MedalRepository

    @InjectMockKs
    private lateinit var resetMedals: ResetMedals

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given repository when invoke is called then delegate to repository resetMedals`() = runTest {
        coEvery { repository.resetMedals() } returns Unit

        resetMedals()

        coVerify(exactly = 1) { repository.resetMedals() }
    }
}
