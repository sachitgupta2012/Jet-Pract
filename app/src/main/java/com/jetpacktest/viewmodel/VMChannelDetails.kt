package com.jetpacktest.viewmodel

import androidx.lifecycle.ViewModel
import com.jetpacktest.data.RepositoryClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VMChannelDetails @Inject constructor(private val repositoryClass: RepositoryClass): ViewModel() {
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()


    fun setShowDialog(value: Boolean){
        _showDialog.value = value
    }

}