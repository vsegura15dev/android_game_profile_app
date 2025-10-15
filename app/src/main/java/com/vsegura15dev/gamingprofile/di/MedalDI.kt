package com.vsegura15dev.gamingprofile.di

import android.content.Context
import com.vsegura15dev.gamingprofile.data.datasource.LocalDataSource
import com.vsegura15dev.gamingprofile.data.datasource.MedalDataSource
import com.vsegura15dev.gamingprofile.data.reader.JsonReader
import com.vsegura15dev.gamingprofile.data.repository.MedalRepositoryImpl
import com.vsegura15dev.gamingprofile.domain.generator.RandomNumberGenerator
import com.vsegura15dev.gamingprofile.domain.generator.RandomNumberGeneratorImpl
import com.vsegura15dev.gamingprofile.domain.repository.MedalRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MedalDI {

    @Provides
    fun providesRandomNumberGenerator(): RandomNumberGenerator {
        return RandomNumberGeneratorImpl()
    }

    @Provides
    @DispatcherIO
    fun providesIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun providesLocalDataSourceImpl(
        @ApplicationContext context: Context,
        jsonReader: JsonReader,
        @DispatcherIO dispatcher: CoroutineDispatcher
    ): LocalDataSource {
        return LocalDataSource(context, jsonReader, dispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaBindingDI {

    @Binds
    abstract fun providesMedalRepository(impl: MedalRepositoryImpl): MedalRepository


    @com.vsegura15dev.gamingprofile.di.LocalDataSource
    @Binds
    abstract fun providesLocalDataSource(impl: LocalDataSource): MedalDataSource
}