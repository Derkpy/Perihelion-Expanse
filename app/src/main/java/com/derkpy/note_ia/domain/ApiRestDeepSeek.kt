package com.derkpy.note_ia.domain

import com.derkpy.note_ia.domain.model.deepseek.DeepSeekRequest
import com.derkpy.note_ia.domain.model.deepseek.DeepSeekResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRestDeepSeek {

    @POST("chat/completions")
    suspend fun chatCompletion(
        @Body request: DeepSeekRequest
    ): DeepSeekResponse

}