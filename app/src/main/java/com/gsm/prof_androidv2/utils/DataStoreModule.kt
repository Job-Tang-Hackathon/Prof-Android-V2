package com.gsm.prof_androidv2.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gsm.prof_androidv2.utils.DataStoreModule.PreferencesKeys.dataStoreToken
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreModule @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore by preferencesDataStore("uid")

    private object PreferencesKeys {
        val dataStoreToken = stringPreferencesKey("uid")
    }

    //private val intKey = intPreferencesKey("key_name") // int 저장 키값
    private val dataStore: DataStore<Preferences> =
        context.dataStore


    suspend fun setUid(text: String) {
        context.dataStore.edit { preferences ->
            preferences[dataStoreToken] = text
        }
    }


    val readUid: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[dataStoreToken] ?: ""
        }

}