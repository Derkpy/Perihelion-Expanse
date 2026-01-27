package com.derkpy.note_ia.ui.home.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.derkpy.note_ia.domain.DataRepository
import com.derkpy.note_ia.domain.model.NoteModel
import com.derkpy.note_ia.domain.model.SubTaskModel
import com.derkpy.note_ia.domain.model.TaskModel
import com.derkpy.note_ia.ui.home.contract.FormState
import com.derkpy.note_ia.ui.home.contract.HomeEvent
import com.derkpy.note_ia.ui.home.contract.HomeUiState
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.SheetMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class HomeViewModel constructor(private val repository: DataRepository) : ViewModel(){

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _showSheet = MutableStateFlow(false)
    val showSheet: StateFlow<Boolean> = _showSheet.asStateFlow()

    private val _expanded = MutableStateFlow(false)
    val expanded: StateFlow<Boolean> = _expanded.asStateFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {

            is HomeEvent.TitleNoteChanged -> {

                _uiState.update {
                    it.copy(noteState = it.noteState.copy(title = event.title))
                }

            }
            is HomeEvent.DescriptionNoteChanged -> {
                _uiState.update {
                    it.copy(noteState = it.noteState.copy(description = event.description))
                }
            }

            is HomeEvent.SaveNote -> {

                val title = _uiState.value.noteState.title
                val description = _uiState.value.noteState.description

                if (title.isBlank()) {
                    _uiState.update { it.copy(error = "El título no puede estar vacío") }
                    return
                } else {

                    viewModelScope.launch {

                        _uiState.update { it.copy(isSavingNote = true) }

                        val result = repository.makePostSaveNote(
                            NoteModel(

                                title = title,
                                description = description,
                                date = System.currentTimeMillis()
                            )
                        )

                        result.onSuccess { docId ->
                            _uiState.update {
                                it.copy(
                                    isSavingNote = false,
                                    noteState = FormState(),
                                )
                            }
                        }.onFailure {
                            _uiState.update {
                                it.copy(
                                    isSavingNote = false,
                                    error = it.error ?: "Error desconocido"
                                )
                            }
                        }
                    }
                    toggleBottomSheetVisibility()
                }
            }

            is HomeEvent.ModeSheetChanged -> {
                _uiState.update {
                    it.copy(
                        modeSheet = event.mode
                    )
                }
            }

            is HomeEvent.SubTaskChanged -> {
                _uiState.update {
                    it.copy(
                        taskState = it.taskState.copy(contentTask = event.subTask)
                    )
                }
            }

            HomeEvent.SaveTask ->
            {
                println("Estoy en el save task")
                val title = _uiState.value.taskState.title
                val description = _uiState.value.taskState.description
                val content = _uiState.value.taskState.contentTask

                if (title.isBlank() && content.isBlank()) {
                    _uiState.update { it.copy(error = "Complete los campos faltantes") }
                    println("Agarro el error: ${_uiState.value.error}")
                    return
                } else {
                    viewModelScope.launch {

                        _uiState.update { it.copy(isSavingTask = true) }

                        val result = repository.makePostSaveTask(
                            TaskModel(
                                title = title,
                                description = description,
                                content = content.split("\n").map { title ->
                                    SubTaskModel(
                                        id = UUID.randomUUID().toString(),
                                        name = title
                                    ) },
                                date = System.currentTimeMillis()
                            )
                        )
                        result.onSuccess {
                            docId ->
                            _uiState.update {
                                    it.copy(
                                        isSavingTask = false,
                                        taskState = FormState(),
                                    )
                            }
                        }.onFailure {
                            _uiState.update {
                                    it.copy(
                                        isSavingTask = false,
                                        error = it.error ?: "Error desconocido"
                                    )
                            }
                        }
                    }
                }
                toggleBottomSheetVisibility()
            }

            is HomeEvent.DescriptionTaskChanged -> {
                _uiState.update {
                    it.copy(taskState = it.taskState.copy(description = event.description))
                }
            }


            is HomeEvent.TitleTaskChanged -> {
                _uiState.update {
                    it.copy(taskState = it.taskState.copy(title = event.title))
                }
            }

            HomeEvent.GenerateSubtasks -> {

                _uiState.update { it.copy(isLoading = true) }

                val title = _uiState.value.taskState.title

                if (title.isBlank()) {
                    _uiState.update { it.copy(error = "El título no puede estar vacío") }
                    return
                } else {
                    viewModelScope.launch {

                        val result = repository.generateContent("Crea una lista de subtareas para la tarea de: $title")

                        result.onSuccess {
                            _uiState.update { it.copy(taskState = it.taskState.copy(contentTask = result.toString())) }

                        }.onFailure {
                            _uiState.update { it.copy(error = result.toString()) }
                        }
                    }
                }
            }
        }
    }

    private fun observerNotes(){

        _uiState.update { it.copy(isLoading = true) }

        val currentUser = repository.currentUser()?.uid

        viewModelScope.launch {
            repository.requestTasks(currentUser.toString()).collect { tasks ->
                _uiState.update { state ->
                    state.copy(listTasks = tasks)
                }
            }
        }

        viewModelScope.launch {

            repository.requestNotes(currentUser.toString()).collect { notes ->
                _uiState.update { state ->
                    state.copy(listNotes = notes)
                }
            }
        }

    }

    //fun to testing
    /*
    fun observer() {
        observerNotes()
    }*/

    init {

        observerNotes()

    }

    fun closeSession(){

        repository.logoutSession()

    }

    fun toggleBottomSheetVisibility() {
        _showSheet.value = !_showSheet.value
    }

    fun visibilityExpanded(){
        _expanded.value = !_expanded.value
    }

    fun openBottomSheet(mode : SheetMode){

        viewModelScope.launch {

            visibilityExpanded()

            onEvent(event = HomeEvent.ModeSheetChanged(mode = mode))

            toggleBottomSheetVisibility()

        }
    }
}