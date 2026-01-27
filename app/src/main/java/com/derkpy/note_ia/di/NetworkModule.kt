package com.derkpy.note_ia.di

import com.derkpy.note_ia.BuildConfig
import com.derkpy.note_ia.domain.ApiRestDeepSeek
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            prettyPrint = true
        }
    }
    single {
        HttpLoggingInterceptor().apply {

            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor {chain ->
                val request = chain.request().newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.DEEPSEEK_API_KEY}")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(get<HttpLoggingInterceptor>())
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single<Retrofit> {
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl("https://api.deepseek.com/")
            .addConverterFactory(get<Json>().asConverterFactory(contentType))
            .client(get<OkHttpClient>())
            .build()
    }
    single { get<Retrofit>().create(ApiRestDeepSeek::class.java) }
}