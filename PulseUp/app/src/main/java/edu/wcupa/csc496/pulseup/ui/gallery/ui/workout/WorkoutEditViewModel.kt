package edu.wcupa.csc496.pulseup.ui.gallery.ui.workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import edu.wcupa.csc496.pulseup.ui.gallery.data.WorkoutRepository

class WorkoutEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {

    private val workoutId: Int = checkNotNull(savedStateHandle[WorkoutEditDestination.workoutIdArg])

    /**
     * Holds current item ui state
     */
    var workoutUiState by mutableStateOf(WorkoutUiState())
        private set

    init {
        viewModelScope.launch {
            workoutUiState = workoutRepository.getWorkoutStream(workoutId)
                .filterNotNull()
                .first()
                .toWorkoutUiState(true)
        }
    }


    private fun validateInput(uiState: WorkoutDetails = workoutUiState.workoutDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && reps.isNotBlank()
        }
    }

    fun updateUiState(workoutDetails: WorkoutDetails) {
        workoutUiState =
            WorkoutUiState(workoutDetails = workoutDetails, isEntryValid = validateInput(workoutDetails))
    }

    suspend fun updateWorkout() {
        if (validateInput(workoutUiState.workoutDetails)) {
            //workoutRepository.updateWorkout(workoutUiState.workoutDetails.toWorkout())
            val currentWorkout = workoutRepository.getWorkoutStream(workoutId)
                .filterNotNull()
                .first()
            val updatedWorkout = workoutUiState.workoutDetails.toWorkout().copy(id = currentWorkout.id)
            workoutRepository.updateWorkout(updatedWorkout)
        }
    }
}

