package com.derkpy.note_ia

import android.app.Application
import com.derkpy.note_ia.di.credentialManager
import com.derkpy.note_ia.di.dataRepositoryModule
import com.derkpy.note_ia.di.dataSourceModule
import com.derkpy.note_ia.di.firebaseModule
import com.derkpy.note_ia.di.googleHelperModule
import com.derkpy.note_ia.di.mainViewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(firebaseModule,
                credentialManager,
                dataRepositoryModule,
                dataSourceModule,
                googleHelperModule,
                mainViewModule
            )
        }
    }
}
