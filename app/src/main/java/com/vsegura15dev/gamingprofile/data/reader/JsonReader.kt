package com.vsegura15dev.gamingprofile.data.reader

import android.content.Context
import androidx.annotation.RawRes
import com.vsegura15dev.gamingprofile.R
import com.vsegura15dev.gamingprofile.data.dto.MedalDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class JsonReader @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun loadInitMedals(): List<MedalDTO> {
        val inputStream = context.resources.openRawResource(R.raw.init_medals)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }
}