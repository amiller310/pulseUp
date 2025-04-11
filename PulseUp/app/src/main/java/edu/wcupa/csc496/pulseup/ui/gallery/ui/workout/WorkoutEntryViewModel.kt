package edu.wcupa.csc496.pulseup.ui.gallery.ui.workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import edu.wcupa.csc496.pulseup.ui.gallery.data.Workout
import edu.wcupa.csc496.pulseup.ui.gallery.data.WorkoutRepository

class WorkoutEntryViewModel(private val workoutRepository: WorkoutRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var workoutUiState by mutableStateOf(WorkoutUiState())
        private set

    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(workoutDetails: WorkoutDetails) {
        workoutUiState =
            WorkoutUiState(workoutDetails = workoutDetails, isEntryValid = validateInput(workoutDetails))
    }

    private fun validateInput(uiState: WorkoutDetails = workoutUiState.workoutDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && reps.isNotBlank()
        }
    }

    suspend fun saveWorkout() {
        if (validateInput()) {
            workoutRepository.insertWorkout(workoutUiState.workoutDetails.toWorkout())
        }
    }

}

/**
 * Represents Ui State for an Item.
 */
data class WorkoutUiState(
    val workoutDetails: WorkoutDetails = WorkoutDetails(),
    val isEntryValid: Boolean = false
)

data class WorkoutDetails(
    val id: Int = 0,
    val name: String = "",
    val reps: String = "",
    val notes: String = "",
)

/**
 * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun WorkoutDetails.toWorkout(): Workout = Workout(
    id = id,
    name = name,
    reps = reps.toIntOrNull() ?: 0,
    notes = notes,
)

fun Workout.formatedReps(): String {
    return (reps.toString() + " reps/lbs.")
}

/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun Workout.toWorkoutUiState(isEntryValid: Boolean = false): WorkoutUiState = WorkoutUiState(
    workoutDetails = this.toWorkoutDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun Workout.toWorkoutDetails(): WorkoutDetails = WorkoutDetails(
    id = id,
    name = name,
    reps = reps.toString(),
    notes = notes
)
