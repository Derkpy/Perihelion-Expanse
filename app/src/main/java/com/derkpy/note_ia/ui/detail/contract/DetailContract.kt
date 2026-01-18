package com.derkpy.note_ia.ui.detail.contract

import com.derkpy.note_ia.domain.model.TaskModel


sealed class DetailEvent{

    data class TitleChanged(val title: String) : DetailEvent()
    data class DescriptionChanged(val description: String) : DetailEvent()
    data class SubTaskChanged(val subTask : String) : DetailEvent()

    data class StateTextFieldEnabled(val enable: Boolean) : DetailEvent()

    object EditTask : DetailEvent()

}

data class DetailUiState(

    val isLoading: Boolean = false,
    val error : String? = null,
    val editTask: Boolean = false,

    val task: TaskModel = TaskModel(),

    val enableTextField : Boolean = false

)