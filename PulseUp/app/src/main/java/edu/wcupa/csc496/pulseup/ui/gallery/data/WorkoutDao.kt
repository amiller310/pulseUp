package edu.wcupa.csc496.pulseup.ui.gallery.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workout: Workout)

    @Update
    suspend fun update(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

    @Query("SELECT * FROM workouts WHERE id = :id")
    fun getWorkout(id: Int): Flow<Workout>

    @Query("SELECT * FROM workouts")
    fun getAllWorkouts(): Flow<List<Workout>>
}