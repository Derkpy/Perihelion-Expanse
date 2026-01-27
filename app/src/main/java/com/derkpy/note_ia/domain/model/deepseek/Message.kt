package com.derkpy.note_ia.domain.model.deepseek

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val role: String,
    val content: String
)