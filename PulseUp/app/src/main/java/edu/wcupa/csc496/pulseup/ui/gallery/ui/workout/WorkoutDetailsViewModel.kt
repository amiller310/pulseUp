package edu.wcupa.csc496.pulseup.ui.gallery.ui.workout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.wcupa.csc496.pulseup.ui.gallery.data.WorkoutRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve, update and delete an item from the [ItemsRepository]'s data source.
 */
class WorkoutDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: WorkoutRepository,
) : ViewModel() {

    private val workoutId: Int = checkNotNull(savedStateHandle[WorkoutDetailsDestination.workoutIdArg])

    /**
     * Holds the item details ui state. The data is retrieved from [ItemsRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<WorkoutDetailsUiState> =
        workoutRepository.getWorkoutStream(workoutId)
            .filterNotNull()
            .map {
                WorkoutDetailsUiState(/*outOfStock = it.quantity <= 0,*/ workoutDetails = it.toWorkoutDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WorkoutDetailsUiState()
            )

    /**
     * Reduces the item quantity by one and update the [ItemsRepository]'s data source.
     */
    /*fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentItem = uiState.valueDetails.toItem()
            if (currentItem.quantity > 0) {
                itemsRepository.updateItem(currentItem.copy(quantity = currentItem.quantity - 1))
            }
        }
    }*/

    /**
     * Deletes the item from the [ItemsRepository]'s data source.
     */
    suspend fun deleteWorkout() {
        workoutRepository.deleteWorkout(uiState.value.workoutDetails.toWorkout())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for ItemDetailsScreen
 */
data class WorkoutDetailsUiState(
    //val outOfStock: Boolean = true,
    val workoutDetails: WorkoutDetails = WorkoutDetails()
)