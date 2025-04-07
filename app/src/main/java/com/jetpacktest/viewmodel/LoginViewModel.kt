package com.jetpacktest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.jetpacktest.data.Models
import com.jetpacktest.data.RepositoryClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: RepositoryClass): ViewModel() {
    private val _loginState = MutableStateFlow<Models.LoginResponse?>(null)
    val loginState : StateFlow<Models.LoginResponse?> = _loginState

    private val _userToken = MutableStateFlow<String?>(null)
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getUserToken().collectLatest { token ->
                _userToken.value = token
            }
        }
    }

    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }

    fun login(userEmail: String, userPassword: String, fcmToken: String){
        val jsonObject = JsonObject()
        jsonObject.addProperty("UserId", userEmail)
        jsonObject.addProperty("Password", userPassword)
        jsonObject.addProperty("UserRole", "user")
        jsonObject.addProperty("FcmDeviceToken", fcmToken)

        viewModelScope.launch {
            repository.loginUser(jsonObject).collect{response->
                if (response.isSuccessful){
                    _loginState.value = response.body()
                    response.body()?.let {
                        if (response.body()!!.StatusCode == 200) {
                            repository.saveUserData(it)
                        }else{
                            _showDialog.value = true
                        }
                    }

                }else{
                    _loginState.value = Models.LoginResponse()
                }
            }
        }

    }
}