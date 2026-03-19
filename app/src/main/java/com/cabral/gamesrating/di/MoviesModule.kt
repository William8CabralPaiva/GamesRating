package com.cabral.gamesrating.di

import com.cabral.gamesrating.ui.GamesSharedViewModel
import com.cabral.gamesrating.usecase.GetAllGamesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object MoviesModule {

    val modules get() = listOf(repositoryModules, useCaseModules, viewModelModules)

    private val viewModelModules: Module = module {
        viewModel {
            GamesSharedViewModel(get())
        }
    }

    private val useCaseModules: Module = module {
        factory {
            GetAllGamesUseCase(get())
        }
    }

    private val repositoryModules: Module = module {
        factory<MoviesRepository> {
            MoviesRepositoryImpl(get())
        }
    }
}