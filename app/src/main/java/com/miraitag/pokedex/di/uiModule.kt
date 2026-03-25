package com.miraitag.pokedex.di

import com.miraitag.pokedex.ui.screens.detail.DetailViewModel
import com.miraitag.pokedex.ui.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
}