package com.derkpy.note_ia.domain.model.deepseek

import kotlinx.serialization.Serializable

@Serializable
data class DeepSeekResponse(
    val id: String,
    val choices: List<Choice>
)
