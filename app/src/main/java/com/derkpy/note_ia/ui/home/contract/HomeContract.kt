package com.derkpy.note_ia.ui.home.contract

import com.derkpy.note_ia.domain.model.NoteModel
import com.derkpy.note_ia.domain.model.TaskModel
import com.derkpy.note_ia.ui.home.ui.components.bottomSheet.SheetMode

sealed class HomeEvent {

    data class TitleNoteChanged(val title: String) : HomeEvent()
    data class DescriptionNoteChanged(val description: String) : HomeEvent()

    data class TitleTaskChanged(val title: String) : HomeEvent()
    data class DescriptionTaskChanged(val description: String) : HomeEvent()
    data class SubTaskChanged(val subTask : String) : HomeEvent()

    data class ModeSheetChanged(val mode: SheetMode) : HomeEvent()


    object SaveNote : HomeEvent()
    object SaveTask : HomeEvent()

}

data class HomeUiState(

    val noteState: FormState = FormState(),
    val taskState: FormState = FormState(),

    val isLoading: Boolean = false,
    val error : String? = null,

    val isSavingNote: Boolean = false,
    val isSavingTask: Boolean = false,

    val listNotes: List<NoteModel> = emptyList(),
    val listTasks: List<TaskModel> = emptyList(),

    val modeSheet : SheetMode? = null

)

data class FormState(

    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val contentTask: String = ""

)
