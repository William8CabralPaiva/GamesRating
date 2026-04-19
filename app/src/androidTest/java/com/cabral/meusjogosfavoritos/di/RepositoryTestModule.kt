package com.cabral.meusjogosfavoritos.di

import com.cabral.meusjogosfavoritos.data.repository.FakeGamesRepository
import com.cabral.meusjogosfavoritos.domain.repository.GamesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
abstract class RepositoryTestModule {

    @Binds
    @Singleton
    abstract fun bindGamesRepository(
        fakeGamesRepository: FakeGamesRepository
    ): GamesRepository

    companion object {
        @Provides
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}
