package edu.wcupa.csc496.pulseup.ui.gallery.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

@Database(entities = [Workout::class], version = 1, exportSchema = false)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun WorkoutDao(): WorkoutDao
    companion object {
        @Volatile
        private var Instance: WorkoutDatabase? = null
        fun getDatabase(context: Context): WorkoutDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WorkoutDatabase::class.java, "workout_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}