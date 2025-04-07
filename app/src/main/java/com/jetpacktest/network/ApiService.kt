package com.jetpacktest.network

import com.google.gson.JsonObject
import com.jetpacktest.data.AppConstant
import com.jetpacktest.data.Models
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST(AppConstant.EndUrl.loginUser)
    suspend fun userLogin(
        @Body requestParam: JsonObject
    ): Response<Models.LoginResponse>

    @Headers(AppConstant.headerJSON)
    @POST(AppConstant.EndUrl.getChannelList)
    suspend fun getChannelList(
        @Header("Authorization") authToken: String,
        @Header("accept") accept: String = "application/json",
    ): Response<Models.ChannelListResponse>

    @Headers(AppConstant.headerJSON)
    @POST(AppConstant.EndUrl.getSettingContent)
    suspend fun getContentPage(
        @Header("Authorization") authToken: String,
        @Header("accept") accept: String = "application/json",
    ): Response<Models.ContentResponse>

    @Headers(AppConstant.headerJSON)
    @GET(AppConstant.EndUrl.logout)
    suspend fun logoutUser(
        @Header("Authorization") authToken: String,
        @Header("accept") accept: String = "application/json",
    ): Response<Models.LogoutResponse>


}