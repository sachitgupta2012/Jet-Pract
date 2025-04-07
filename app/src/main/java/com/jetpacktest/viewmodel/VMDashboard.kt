package com.jetpacktest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jetpacktest.data.ApiState
import com.jetpacktest.data.Models
import com.jetpacktest.data.RepositoryClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class VMDashboard @Inject constructor(private val repository: RepositoryClass): ViewModel() {

    private val _channelListState = MutableStateFlow<ApiState<Models.ChannelListResponse>>(ApiState.Loading)
    val itemsState: StateFlow<ApiState<Models.ChannelListResponse>> = _channelListState
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()


    fun setShowDialog(value: Boolean){
        _showDialog.value = value
    }
    fun getChannelList(userToken: String){
        viewModelScope.launch {
            try {
                _channelListState.value = ApiState.Loading
                repository.getChannelList(userToken).collect{response->
                    if (response.isSuccessful && response.body() != null){
                       response.body().let {
                           response.body()?.let {
                               _channelListState.value = ApiState.Success(it)
                           } ?: run {
                               _showDialog.value = true
                               _channelListState.value = ApiState.Error(it?.Message ?: "")
                           }
                       }
                    }else{
                        _showDialog.value = true
                        _channelListState.value = ApiState.Error(parseErrorBody(response.errorBody()))
                    }
                }
            }
            catch (ex: Exception){
                ex.printStackTrace()
            }
        }
    }

    private fun parseErrorBody(errorBody: ResponseBody?): String {
        return try {
            val gson = Gson()
            val errorResponse = gson.fromJson(errorBody?.charStream(), Models.ChannelListResponse::class.java)
            errorResponse.Message.ifEmpty { "Unknown error occurred" }
        } catch (ex: Exception) {
            "Failed to parse error response"
        }
    }


    fun logout(){
        viewModelScope.launch {
            repository.clearUserData()
        }
    }
}
