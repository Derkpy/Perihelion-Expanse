package com.derkpy.note_ia.di

import androidx.credentials.CredentialManager
import org.koin.dsl.module

val credentialManager = module {

    single<CredentialManager> {
        CredentialManager.create(get())
    }

}