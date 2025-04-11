package edu.wcupa.csc496.pulseup.ui.gallery.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.wcupa.csc496.pulseup.ui.gallery.PRApplication
import edu.wcupa.csc496.pulseup.ui.gallery.ui.home.HomeViewModel
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.WorkoutEditViewModel
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.WorkoutEntryViewModel
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.WorkoutDetailsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            WorkoutEditViewModel(
                this.createSavedStateHandle(),
                PRApplication().container.workoutRepository
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            WorkoutEntryViewModel(PRApplication().container.workoutRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            WorkoutDetailsViewModel(
                this.createSavedStateHandle(),
                PRApplication().container.workoutRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(PRApplication().container.workoutRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.PRApplication(): PRApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as PRApplication)

