package com.fernandopretell.mymoments

import android.content.Context
import android.support.multidex.MultiDexApplication

import timber.log.Timber

class PeruappsApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appContext = applicationContext
    }

    companion object {

        var appContext: Context? = null
            private set
    }

}
