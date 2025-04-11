package edu.wcupa.csc496.pulseup.ui.gallery.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import edu.wcupa.csc496.pulseup.ui.gallery.data.Workout
import edu.wcupa.csc496.pulseup.ui.gallery.data.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

enum class SortOption {
    NAME_ASC,
    NAME_DESC,
    REPS_ASC,
    REPS_DESC
}

class HomeViewModel(workoutRepository: WorkoutRepository) : ViewModel() {
    private val _currentSortOption = MutableStateFlow(SortOption.NAME_ASC)

    private val _workouts = MutableStateFlow<List<Workout>>(emptyList())

    init {
        viewModelScope.launch {
            workoutRepository.getAllWorkoutStream().collect { workouts ->
                _workouts.value = workouts
            }
        }
    }

    val homeUiState: StateFlow<HomeUiState> = combine(_workouts, _currentSortOption) { workouts, sortOption ->
        val sortedWorkouts = when (sortOption) {
            SortOption.NAME_ASC -> workouts.sortedBy { it.name }
            SortOption.NAME_DESC -> workouts.sortedByDescending { it.name }
            SortOption.REPS_ASC -> workouts.sortedBy { it.reps }
            SortOption.REPS_DESC -> workouts.sortedByDescending { it.reps }
        }
        HomeUiState(sortedWorkouts)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = HomeUiState()
    )

    fun updateSortOption(option: SortOption) {
        _currentSortOption.value = option
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val workoutList: List<Workout> = listOf())