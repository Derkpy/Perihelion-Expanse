package com.derkpy.note_ia.ui.detail.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.derkpy.note_ia.core.navigation.Detail
import com.derkpy.note_ia.domain.DataRepository
import com.derkpy.note_ia.ui.detail.contract.DetailEvent
import com.derkpy.note_ia.ui.detail.contract.DetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetailViewModel constructor(
    savedStateHandle: SavedStateHandle,
    repository: DataRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val detailRoute = savedStateHandle.toRoute<Detail>()
    val noteId = detailRoute.id

    fun onEvent(event: DetailEvent){

        when(event){

            is DetailEvent.EditTask -> TODO()

            is DetailEvent.DescriptionChanged -> TODO()

            is DetailEvent.SubTaskChanged -> TODO()

            is DetailEvent.TitleChanged -> TODO()

            is DetailEvent.StateTextFieldEnabled ->
                _uiState.update { it.copy(
                    enableTextField = true
                ) }

        }

    }

    fun getTask() {

    }

}