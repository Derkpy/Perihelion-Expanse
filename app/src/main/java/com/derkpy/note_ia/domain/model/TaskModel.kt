package com.derkpy.note_ia.domain.model


data class TaskModel(
    val id: String = "",
    val title: String = "",
    val description : String = "",
    val content : List<SubTaskModel> = emptyList(),
    val date: Long = 0
)

data class SubTaskModel(
    val id: String = "",
    val name : String = ""
)