package edu.wcupa.csc496.pulseup.ui.gallery.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val reps: Int,
    val notes: String,
)
