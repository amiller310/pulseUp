package edu.wcupa.csc496.pulseup.ui.gallery.data

import kotlinx.coroutines.flow.Flow

class OfflineWorkoutRepository(private val workoutDao: WorkoutDao) : WorkoutRepository {
    override fun getAllWorkoutStream(): Flow<List<Workout>> = workoutDao.getAllWorkouts()

    override fun getWorkoutStream(id: Int): Flow<Workout?> = workoutDao.getWorkout(id)

    override suspend fun insertWorkout(workout: Workout) = workoutDao.insert(workout)

    override suspend fun deleteWorkout(workout: Workout) = workoutDao.delete(workout)

    override suspend fun updateWorkout(workout: Workout) = workoutDao.update(workout)

}