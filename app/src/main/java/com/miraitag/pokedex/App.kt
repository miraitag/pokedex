package com.miraitag.pokedex

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

class App : Application() {

    /*lateinit var db: PokemonDataBase
        private set*/

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(appModule)
        }
        /*db = Room.databaseBuilder(
            context = this,
            klass = PokemonDataBase::class.java,
            name = "pokemon_database"
        ).build()*/
    }
}

val appModule = module {
    single(named("apiKey")) { BuildConfig.POKEMONS_API_KEY }
    factory {  }
}