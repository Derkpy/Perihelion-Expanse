package com.derkpy.note_ia.ui.detail.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.derkpy.note_ia.core.navigation.Detail
import com.derkpy.note_ia.domain.DataRepository
import com.derkpy.note_ia.ui.detail.contract.DetailEvent
import com.derkpy.note_ia.ui.detail.contract.DetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: DataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val detailRoute = savedStateHandle.toRoute<Detail>()
    val noteId = detailRoute.id

    fun onEvent(event: DetailEvent){

        when(event){

            is DetailEvent.EditTask -> {
                _uiState.update { it.copy(isLoading = true) }

                viewModelScope.launch {

                    repository.updateTask(task = _uiState.value.task)

                }

                _uiState.update {
                    it.copy(
                        enableTextField = false,
                        isLoading = false
                    )
                }
            }

            is DetailEvent.DescriptionChanged -> {
                _uiState.update { it.copy(task = it.task.copy(description = event.description)) }
            }

            is DetailEvent.SubTaskChanged -> {

                val newSubtask = _uiState.value.task.content.map {
                    if (it.id == event.subTask.id) event.subTask else it
                }

                _uiState.update { it.copy(task = it.task.copy(content = newSubtask)) }

            }
            is DetailEvent.TitleChanged -> {
                _uiState.update { it.copy(task = it.task.copy(title = event.title)) }
            }

            is DetailEvent.StateTextFieldEnabled -> {

                _uiState.update { it.copy(isLoading = true) }

                _uiState.update {
                    it.copy(
                        enableTextField = event.enable,
                        isLoading = false
                    )
                }
            }

            is DetailEvent.StateAddTask ->
                _uiState.update {
                    it.copy(addSubTask = event.enable)
                }
        }
    }

    private fun getTask() {

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {

            val task = repository.getTask(noteId)

            if (task != null) {
                _uiState.update {
                    it.copy(task = task)
                }
            }
        }

        _uiState.update { it.copy(isLoading = false) }

    }

    init {
        getTask()
    }

}