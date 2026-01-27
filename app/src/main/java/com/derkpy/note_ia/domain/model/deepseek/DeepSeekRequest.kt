package com.derkpy.note_ia.domain.model.deepseek

import kotlinx.serialization.Serializable

@Serializable
data class DeepSeekRequest(
    val model: String = "deepseek-chat",
    val messages: List<Message>,
    val temperature: Double = 0.7,
    val stream: Boolean = false
)