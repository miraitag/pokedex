package com.miraitag.pokedex

import android.app.Application
import androidx.room.Room
import com.miraitag.pokedex.data.local.datasource.database.PokemonDataBase

class App : Application() {

    lateinit var db: PokemonDataBase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            context = this,
            klass = PokemonDataBase::class.java,
            name = "pokemon_database"
        ).build()
    }
}