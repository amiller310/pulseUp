package edu.wcupa.csc496.pulseup.ui.gallery

import android.app.Application
import edu.wcupa.csc496.pulseup.ui.gallery.data.AppContainer
import edu.wcupa.csc496.pulseup.ui.gallery.data.AppDataContainer

// this should also be at the top level with the MainActivity

class PRApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}