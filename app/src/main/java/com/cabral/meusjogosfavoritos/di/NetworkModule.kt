package com.cabral.meusjogosfavoritos.di

import com.cabral.meusjogosfavoritos.data.remote.GamesApi
import com.cabral.meusjogosfavoritos.data.remote.TranslationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("GamesRetrofit")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("TranslationRetrofit")
    fun provideTranslationRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mymemory.translated.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesApi(@Named("GamesRetrofit") retrofit: Retrofit): GamesApi {
        return retrofit.create(GamesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTranslationApi(@Named("TranslationRetrofit") retrofit: Retrofit): TranslationApi {
        return retrofit.create(TranslationApi::class.java)
    }
}