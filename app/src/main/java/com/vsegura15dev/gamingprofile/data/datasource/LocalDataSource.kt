package com.vsegura15dev.gamingprofile.data.datasource

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vsegura15dev.gamingprofile.data.dto.MedalDTO
import com.vsegura15dev.gamingprofile.data.reader.JsonReader
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val jsonReader: JsonReader,
    private val dispatcher: CoroutineDispatcher
) : MedalDataSource {

    companion object {
        private const val STORE_NAME = "medals_prefs"
        private const val PREFERENCES_KEY = "medals"
    }

    private val Context.preferencesDataStore by preferencesDataStore(STORE_NAME)
    private val medalsKey = stringPreferencesKey(PREFERENCES_KEY)

    override fun getMedals(): Flow<List<MedalDTO>?> {
        return context.preferencesDataStore.data.map { prefs ->
            prefs[medalsKey]?.let {
                Log.d(this::class.java.simpleName, "data: $it")
                Json.decodeFromString<List<MedalDTO>>(it)
            } ?: run {
                CoroutineScope(dispatcher).launch {
                    saveMedals(jsonReader.loadInitMedals())
                }
                emptyList()
            }
        }
    }

    override fun saveMedal(medal: MedalDTO) {

    }

    override suspend fun saveMedals(medals: List<MedalDTO>) {
        context.preferencesDataStore.edit { prefs ->
            prefs[medalsKey] = Json.encodeToString(medals)
        }
    }
}