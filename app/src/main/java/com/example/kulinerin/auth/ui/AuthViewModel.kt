package com.example.kulinerin.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kulinerin.auth.model.Credentials
import com.example.kulinerin.preferences.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val preferences: SessionManager) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(credentials: Credentials) {
    }

    fun logout() {
        viewModelScope.launch { preferences.clearSession() }
    }

    companion object {
        private const val TAG = "UserViewModel"
    }
}