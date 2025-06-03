package com.nyinyi.notemark.di

import org.koin.core.context.startKoin

object KoinInitializer {
    fun init() {
        startKoin {
            modules(appModule)
        }
    }
}
