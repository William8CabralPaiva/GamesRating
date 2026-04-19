package com.cabral.meusjogosfavoritos.di

import com.cabral.meusjogosfavoritos.data.repository.GamesRepositoryImpl
import com.cabral.meusjogosfavoritos.data.repository.TranslationRepositoryImpl
import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import com.cabral.meusjogosfavoritos.domain.repository.TranslationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindGamesRepository(
        impl: GamesRepositoryImpl,
    ): GamesRepository

    @Binds
    @Singleton
    abstract fun bindTranslationRepository(
        impl: TranslationRepositoryImpl,
    ): TranslationRepository

    companion object {
        @Provides
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}