package com.jetpacktest.data

import com.google.gson.JsonObject
import com.jetpacktest.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryClass @Inject constructor(
    private val apiService: ApiService,
    private val dataStore: DataStore
) {
    fun loginUser(jsonObject: JsonObject): Flow<Response<Models.LoginResponse>> = flow {
        emit(apiService.userLogin(jsonObject))
    }

    fun getChannelList(userToken: String): Flow<Response<Models.ChannelListResponse>> = flow {
        emit(apiService.getChannelList("Bearer $userToken"))
    }

    fun getContentSettingPage(userToken: String): Flow<Response<Models.ContentResponse>> = flow {
        emit(apiService.getContentPage("Bearer $userToken"))
    }

    fun logoutUser(userToken: String): Flow<Response<Models.LogoutResponse>> = flow {
        emit(apiService.logoutUser("Bearer $userToken"))
    }

    //data store to store local data
    suspend fun saveUserData(response: Models.LoginResponse){
        dataStore.saveUserData(response)
    }
    fun getUserToken(): Flow<String?> = dataStore.getUserToken()
    fun getUserId(): Flow<String?> = dataStore.getUserId()
    fun getUserEmail(): Flow<String?> = dataStore.getUserEmail()
    fun getUserName(): Flow<String?> = dataStore.getUserName()
    fun getUserMobileNumber(): Flow<String?> = dataStore.getUserMobileNumber()

    suspend fun clearUserData() {
        dataStore.clearUserData()
    }
}