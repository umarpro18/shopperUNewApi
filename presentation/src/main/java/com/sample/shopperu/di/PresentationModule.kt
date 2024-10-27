package com.sample.shopperu.di

import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)
}