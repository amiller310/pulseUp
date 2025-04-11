package edu.wcupa.csc496.pulseup.ui.gallery.data

import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getAllWorkoutStream(): Flow<List<Workout>>

    fun getWorkoutStream(id: Int): Flow<Workout?>

    suspend fun insertWorkout(workout: Workout)

    suspend fun deleteWorkout(workout: Workout)

    suspend fun updateWorkout(workout: Workout)
}