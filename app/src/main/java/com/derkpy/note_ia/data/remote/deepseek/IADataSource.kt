package com.derkpy.note_ia.data.remote.deepseek

interface IADataSource {

    suspend fun generateContent(userPrompt : String) : Result<String>

}