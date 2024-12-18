package com.zybooks.practicepals.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.practicepals.database.entities.User
import com.zybooks.practicepals.database.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserAuthState {
    object Loading : UserAuthState()
    data class Success(val user: User) : UserAuthState()
    data class Error(val message: String) : UserAuthState()
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _authState = MutableLiveData<UserAuthState>()
    val authState: LiveData<UserAuthState> = _authState

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    fun signUp(email: String, password: String, username: String) {
        _authState.value = UserAuthState.Loading
        viewModelScope.launch {
            userRepository.createUser(email, password, username)
                .onSuccess { user ->
                    _currentUser.value = user
                    _authState.value = UserAuthState.Success(user)
                }
                .onFailure { exception ->
                    _authState.value = UserAuthState.Error(exception.message ?: "Sign up failed")
                }
        }
    }

    fun signIn(email: String, password: String) {
        _authState.value = UserAuthState.Loading
        viewModelScope.launch {
            userRepository.signIn(email, password)
                .onSuccess { user ->
                    _currentUser.value = user
                    _authState.value = UserAuthState.Success(user)
                }
                .onFailure { exception ->
                    _authState.value = UserAuthState.Error(exception.message ?: "Sign in failed")
                }
        }
    }

    fun updateProfilePicture(imageUri: Uri) {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                userRepository.uploadProfilePicture(user.userId, imageUri)
                    .onSuccess { downloadUrl ->
                        _currentUser.value = user.copy(profilePictureUrl = downloadUrl)
                    }
            }
        }
    }
}