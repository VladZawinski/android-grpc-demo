package com.vlad.grpcdemo

import android.app.Application
import com.vlad.grpcdemo.di.bindModule
import com.vlad.grpcdemo.di.singletonModule
import com.vlad.grpcdemo.di.viewModelModule
import org.koin.core.context.startKoin
import timber.log.Timber

class GrpcDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            modules(
                singletonModule,
                bindModule,
                viewModelModule
            )
        }
    }
}