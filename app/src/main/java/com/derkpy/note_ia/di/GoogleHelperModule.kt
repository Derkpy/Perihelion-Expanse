package com.derkpy.note_ia.di

import com.derkpy.note_ia.data.remote.google.GoogleHelper
import com.derkpy.note_ia.data.remote.google.GoogleHelperImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val googleHelperModule = module {
    singleOf(::GoogleHelperImp) {
        bind<GoogleHelper>()
    }
}