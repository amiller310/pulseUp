package edu.wcupa.csc496.pulseup.ui.gallery.data

import android.content.Context

interface AppContainer {
    val workoutRepository: WorkoutRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val workoutRepository: WorkoutRepository by lazy {
        OfflineWorkoutRepository(WorkoutDatabase.getDatabase(context).WorkoutDao())
    }
}