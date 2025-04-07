package com.jetpacktest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpacktest.data.ApiState
import com.jetpacktest.data.AppConstant
import com.jetpacktest.data.DataStore
import com.jetpacktest.data.Models
import com.jetpacktest.data.RepositoryClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMSettings @Inject constructor(private val repository: RepositoryClass): ViewModel() {
    private val _contentState = MutableStateFlow<ApiState<Models.ContentResponse>>(ApiState.Loading)
    val contentState: StateFlow<ApiState<Models.ContentResponse>> = _contentState
    private val _logoutState = MutableStateFlow<ApiState<Models.LogoutResponse>>(ApiState.Loading)
    val logoutState: StateFlow<ApiState<Models.LogoutResponse>> = _logoutState
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    fun setShowDialog(value: Boolean){
        _showDialog.value = value
    }

    fun getSettingContent(userToken: String){
        viewModelScope.launch {
            try {
                _contentState.value = ApiState.Loading
                repository.getContentSettingPage(userToken).collect{response->
                    if (response.isSuccessful && response.body() != null){
                        response.body()?.let {
                            _contentState.value = ApiState.Success(it)
                        } ?: run {
                            _showDialog.value = true
                            _contentState.value = ApiState.Error(response.errorBody().toString())
                        }
                    }else{
                        _showDialog.value = true
                        _contentState.value = ApiState.Error(response.errorBody().toString())
                    }
                }
            }
            catch (ex: Exception){
                ex.printStackTrace()
            }
        }
    }
    fun userLogout(userToken: String, dataStore: DataStore){
        viewModelScope.launch {
            try {
                _logoutState.value = ApiState.Loading
                repository.logoutUser(userToken).collect{response->
                    if (response.isSuccessful && response.body() != null){
                        response.body()?.let {
                            _logoutState.value = ApiState.Success(it)
                            dataStore.clearUserData()
                        } ?: run {
                            _showDialog.value = true
                            _logoutState.value = ApiState.Error(response.errorBody().toString())
                        }
                    }else{
                        _showDialog.value = true
                        if (response.code() == 401){
                            _logoutState.value = ApiState.Error(AppConstant.ErrorMsg.unauthorisedUser)
                        }else if (response.code() == 404){
                            _logoutState.value = ApiState.Error(AppConstant.ErrorMsg.notFound)
                        }else{
                            _logoutState.value = ApiState.Error(response.message())
                        }

                    }
                }
            }
            catch (ex: Exception){
                ex.printStackTrace()
            }
        }
    }
}