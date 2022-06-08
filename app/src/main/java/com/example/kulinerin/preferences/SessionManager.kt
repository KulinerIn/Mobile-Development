package com.example.kulinerin.preferences

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("user_preferences")

@Singleton
class SessionManager(appContext: Context) {

    private val userPreferences = appContext.dataStore

    private val token = stringPreferencesKey("token")

    suspend fun setToken(token : String) {
        userPreferences.edit { preferences -> preferences[this.token] = token }
    }

    fun getToken(): Flow<String> {
        return userPreferences.data.map { preferences -> preferences[token] ?: "" }
    }

    suspend fun clearSession() {
        userPreferences.edit { preferences -> preferences.clear() }
    }

}