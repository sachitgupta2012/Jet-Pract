package com.jetpacktest.viewmodel

import androidx.lifecycle.ViewModel
import com.jetpacktest.data.RepositoryClass
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VMSignUp @Inject constructor(private val repositoryClass: RepositoryClass): ViewModel() {

}