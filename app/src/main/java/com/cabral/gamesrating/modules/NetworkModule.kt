package com.cabral.gamesrating.modules

import com.cabral.gamesrating.di.MoviesApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    // 1. Prover o Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 2. Prover a Interface (O Koin resolve o Retrofit automaticamente com get())
    single<MoviesApi> {
        get<Retrofit>().create(MoviesApi::class.java)
    }
}