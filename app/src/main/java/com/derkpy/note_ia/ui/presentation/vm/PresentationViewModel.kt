package com.derkpy.note_ia.ui.presentation.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derkpy.note_ia.BuildConfig
import com.derkpy.note_ia.domain.DataRepository
import com.derkpy.note_ia.vo.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PresentationViewModel constructor(private val repository: DataRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loginWithGoogle(activityContext: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {

                val webClientId = BuildConfig.WEB_CLIENT_ID

                val loginResult = repository.requestSingWithGoogle(activityContext, webClientId)

                if (loginResult != null) {
                    _uiState.update { it.copy(isSuccess = true) }
                } else {
                    _uiState.update { it.copy(error = "Login failed. Please check your credentials.") }
                }

            } catch (e: Exception) {
                val errorMessage = if (e.message?.contains("cancelled") == true) {
                    null
                } else {
                    "Error al iniciar sesión: ${e.message}"
                }

                if (errorMessage != null) {
                    _uiState.update { it.copy(error = errorMessage) }
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onNavigationDone() {
        _uiState.update { it.copy(isSuccess = false, error = null) }
    }
}