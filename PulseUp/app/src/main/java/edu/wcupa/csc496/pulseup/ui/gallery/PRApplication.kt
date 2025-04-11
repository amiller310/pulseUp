package edu.wcupa.csc496.pulseup.ui.gallery

import android.app.Application
import edu.wcupa.csc496.pulseup.ui.gallery.data.AppContainer
import edu.wcupa.csc496.pulseup.ui.gallery.data.AppDataContainer

class PRApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}