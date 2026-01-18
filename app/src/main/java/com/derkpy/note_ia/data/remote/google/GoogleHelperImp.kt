package com.derkpy.note_ia.data.remote.google

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoogleHelperImp(private val credentialManager: CredentialManager
) : GoogleHelper {

    override suspend fun getGoogleIdToken(context: Context, webClientId: String, nonce: String?): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(webClientId)
                    .setAutoSelectEnabled(true)
                    .apply {
                        nonce?.let { setNonce(it) }
                    }
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.e("GoogleAuthHelper", "Error obteniendo credencial", e)
                Result.failure(e)
            } catch (e: Exception) {
                Log.e("GoogleAuthHelper", "Error desconocido", e)
                Result.failure(e)
            }
        }
    }
    private fun handleSignIn(result: GetCredentialResponse): Result<String> {
        val credential = result.credential

        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            return try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken
                Result.success(idToken)
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("GoogleAuthHelper", "Error parseando token", e)
                Result.failure(e)
            }
        } else {
            return Result.failure(Exception("Tipo de credencial no reconocido"))
        }
    }
}
