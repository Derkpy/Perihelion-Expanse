package com.derkpy.note_ia.ui.signup.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derkpy.note_ia.domain.DataRepository
import com.derkpy.note_ia.vo.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SingUpViewModel constructor(private val repository: DataRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _singUpEnable = MutableStateFlow(false)
    val singUpEnable: StateFlow<Boolean> = _singUpEnable.asStateFlow()

    fun onSingUpChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _singUpEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    fun onSingUpSelected() {
        viewModelScope.launch {
            _uiState.update {
                state ->
                state.copy(isLoading = true) }
                val singUpResult = repository.requestCreateAccountWithEmailAndPassword(user = _email.value, password = _password.value)

                if (singUpResult != null) {
                    _uiState.update { it.copy(isSuccess = true) }
                } else {
                    _uiState.update { it.copy(error = "Create Account failed. Please check your credentials.") }
                }

                _uiState.update { it.copy(isLoading = false)
            }
        }
    }

    fun onNavigationDone() {
        _uiState.update { it.copy(isSuccess = false, error = null) }
    }

    private fun isValidEmail(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6
}