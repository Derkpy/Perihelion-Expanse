package com.derkpy.note_ia.di

import com.derkpy.note_ia.domain.DataRepository
import com.derkpy.note_ia.domain.DataRepositoryImp
import org.koin.dsl.module

val appModule = module {

    single<DataRepository> {
        DataRepositoryImp(get(),
            iAData = get()
        )
    }
}