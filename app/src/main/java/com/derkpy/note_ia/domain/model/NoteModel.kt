package com.derkpy.note_ia.domain.model

data class NoteModel(
    val id: String = "",
    val title: String = "",
    val description : String = "",
    val date: Long = 0
)
