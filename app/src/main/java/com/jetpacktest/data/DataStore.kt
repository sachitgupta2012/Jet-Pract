package com.jetpacktest.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property for DataStore
val Context.dataStore by preferencesDataStore("UserLoginResponse")

class DataStore(private val context: Context) {

    companion object {
        private val AUTH_TOKEN = stringPreferencesKey("Token")
        private val USER_ID = stringPreferencesKey("UserId")
        private val USER_EMAIL = stringPreferencesKey("UserEmail")
        private val PASSWORD = stringPreferencesKey("Password")
        private val USER_TYPE = stringPreferencesKey("UserType")
        private val USER_ROLE = stringPreferencesKey("UserRole")
        private val NEW_PASSWORD = stringPreferencesKey("NewPassword")
        private val PROFILE_PIC = stringPreferencesKey("ProfilePic")
        private val NAME = stringPreferencesKey("Name")
        private val MOBILE_NUMBER = stringPreferencesKey("MobileNumber")
        private val IS_FIRST_TIME = booleanPreferencesKey("IsFirstTime")
    }

    suspend fun saveUserData(response: Models.LoginResponse) {
        context.dataStore.edit { prefs ->
            prefs[AUTH_TOKEN] = response.Token
            prefs[USER_ID] = response.Details.UserId
            prefs[USER_EMAIL] = response.Details.UserEmail
            prefs[PASSWORD] = response.Details.Password
            prefs[USER_TYPE] = response.Details.UserType
            prefs[USER_ROLE] = response.Details.UserRole
            prefs[NEW_PASSWORD] = response.Details.NewPassword
            prefs[PROFILE_PIC] = response.Details.ProfilePic
            prefs[NAME] = response.Details.Name
            prefs[MOBILE_NUMBER] = response.Details.MobileNumber
            prefs[IS_FIRST_TIME] = response.Details.IsFirstTime
        }
    }

    fun getUserToken(): Flow<String?> = context.dataStore.data.map { it[AUTH_TOKEN] }
    fun getUserId(): Flow<String?> = context.dataStore.data.map { it[USER_ID] }
    fun getUserEmail(): Flow<String?> = context.dataStore.data.map { it[USER_EMAIL] }
    fun getUserPassword(): Flow<String?> = context.dataStore.data.map { it[PASSWORD] }
    fun getUserType(): Flow<String?> = context.dataStore.data.map { it[USER_TYPE] }
    fun getUserRole(): Flow<String?> = context.dataStore.data.map { it[USER_ROLE] }
    fun getUserNewPassword(): Flow<String?> = context.dataStore.data.map { it[NEW_PASSWORD] }
    fun getUserProfilePic(): Flow<String?> = context.dataStore.data.map { it[PROFILE_PIC] }
    fun getUserName(): Flow<String?> = context.dataStore.data.map { it[NAME] }
    fun getUserMobileNumber(): Flow<String?> = context.dataStore.data.map { it[MOBILE_NUMBER] }

    suspend fun clearUserData() {
        context.dataStore.edit { it.clear() }
    }
}
