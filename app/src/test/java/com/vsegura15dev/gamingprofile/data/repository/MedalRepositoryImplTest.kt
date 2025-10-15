package com.vsegura15dev.gamingprofile.data.repository

import com.vsegura15dev.gamingprofile.data.datasource.LocalDataSource
import com.vsegura15dev.gamingprofile.data.datasource.MedalDataSource
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.*
import org.junit.Before

class MedalRepositoryImplTest {

    @RelaxedMockK
    private lateinit var localDataSource: MedalDataSource

    @InjectMockKs
    private lateinit var repository: MedalRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

}