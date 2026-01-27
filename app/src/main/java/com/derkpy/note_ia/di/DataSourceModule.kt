package com.derkpy.note_ia.di

import com.derkpy.note_ia.data.remote.deepseek.IADataSource
import com.derkpy.note_ia.data.remote.deepseek.IADataSourceImp
import com.derkpy.note_ia.data.repository.DataSource
import com.derkpy.note_ia.data.repository.DataSourceImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModule = module {
     singleOf(::DataSourceImp) {
         bind<DataSource>()
     }
}

val iaDataSourceModule = module {
    singleOf(::IADataSourceImp){
        bind<IADataSource>()
    }
}

