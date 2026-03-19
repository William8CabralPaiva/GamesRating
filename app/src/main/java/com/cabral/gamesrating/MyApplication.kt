package com.cabral.gamesrating

import android.app.Application
import com.cabral.gamesrating.di.MoviesModule
import com.cabral.gamesrating.modules.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(networkModule + MoviesModule.modules)
        }
    }
}