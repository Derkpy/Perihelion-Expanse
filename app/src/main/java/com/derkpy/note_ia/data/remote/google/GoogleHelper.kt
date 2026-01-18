package com.derkpy.note_ia.data.remote.google

import android.content.Context

interface GoogleHelper {

    suspend fun getGoogleIdToken(context: Context, webClientId: String, nonce: String? = null): Result<String>

    }