package edu.wcupa.csc496.pulseup.ui.gallery.ui.workout

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.wcupa.csc496.pulseup.ui.gallery.PRTopAppBar
import edu.wcupa.csc496.pulseup.R
import edu.wcupa.csc496.pulseup.ui.gallery.ui.AppViewModelProvider
import edu.wcupa.csc496.pulseup.ui.gallery.ui.navigation.NavigationDestination
import edu.wcupa.csc496.pulseup.ui.theme.PRTrackerTheme
import kotlinx.coroutines.launch

object WorkoutEditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.edit_workout_title
    const val workoutIdArg = "workoutId"
    val routeWithArgs = "$route/{$workoutIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            PRTopAppBar(
                title = stringResource(WorkoutEditDestination.titleRes),
                isHomeScreen = false,
                isEditScreen = true,
                isEntryScreen = false,
                isDetailsScreen = false,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        WorkoutEntryBody(
            workoutUiState = viewModel.workoutUiState,
            onWorkoutValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateWorkout()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutEditScreenPreview() {
    PRTrackerTheme {
        WorkoutEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}
