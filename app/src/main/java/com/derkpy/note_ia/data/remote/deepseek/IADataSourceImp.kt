package com.derkpy.note_ia.data.remote.deepseek

import com.derkpy.note_ia.domain.ApiRestDeepSeek
import com.derkpy.note_ia.domain.model.deepseek.DeepSeekRequest
import com.derkpy.note_ia.domain.model.deepseek.Message
import com.derkpy.note_ia.ui.theme.systemPrompt
import com.derkpy.note_ia.ui.theme.role

class IADataSourceImp(private val apiService: ApiRestDeepSeek) : IADataSource{

    override suspend fun generateContent(
        userPrompt: String
    ): Result<String> = try {
        val messages = listOf(
            Message(
                role = role,
                content = systemPrompt
            ),
            Message(role = "user", content = userPrompt)
        )

        val request = DeepSeekRequest(messages = messages)

        val response = apiService.chatCompletion(request = request)

        val content = response.choices.firstOrNull()?.message?.content

        if (!content.isNullOrBlank()) {
            Result.success(content)
        } else {
            Result.failure(Exception("La IA respondió vacío"))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }
}