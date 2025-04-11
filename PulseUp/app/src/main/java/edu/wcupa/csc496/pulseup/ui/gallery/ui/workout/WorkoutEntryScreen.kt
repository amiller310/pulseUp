package edu.wcupa.csc496.pulseup.ui.gallery.ui.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import edu.wcupa.csc496.pulseup.ui.gallery.PRTopAppBar
import edu.wcupa.csc496.pulseup.R
import edu.wcupa.csc496.pulseup.ui.gallery.ui.AppViewModelProvider
import edu.wcupa.csc496.pulseup.ui.gallery.ui.navigation.NavigationDestination
import edu.wcupa.csc496.pulseup.ui.theme.PRTrackerTheme

object WorkoutEntryDestination : NavigationDestination {
    override val route = "workout_entry"
    override val titleRes = R.string.workout_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: WorkoutEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            PRTopAppBar(
                title = stringResource(WorkoutEntryDestination.titleRes),
                isHomeScreen = false,
                isEditScreen = false,
                isEntryScreen = true,
                isDetailsScreen = false,
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            WorkoutEntryBody(
                workoutUiState = viewModel.workoutUiState,
                onWorkoutValueChange = viewModel::updateUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.saveWorkout()
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
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun WorkoutEntryBody(
    workoutUiState: WorkoutUiState,
    onWorkoutValueChange: (WorkoutDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.dumbbellseamlesspattern),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            WorkoutInputForm(
                workoutDetails = workoutUiState.workoutDetails,
                onValueChange = onWorkoutValueChange,
                modifier = Modifier
            )
            Button(
                onClick = onSaveClick,
                enabled = workoutUiState.isEntryValid,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.save_action))
            }
        }
    }
}

@Composable
fun WorkoutInputForm(
    workoutDetails: WorkoutDetails,
    modifier: Modifier = Modifier,
    onValueChange: (WorkoutDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = workoutDetails.name,
            onValueChange = { onValueChange(workoutDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.workout_name_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = workoutDetails.reps,
            onValueChange = { onValueChange(workoutDetails.copy(reps = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.workout_reps_req)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = workoutDetails.notes,
            onValueChange = { onValueChange(workoutDetails.copy(notes = it)) },
            label = { Text(stringResource(R.string.workout_notes)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
    if (enabled) {
        Box (modifier = modifier.background(Color(0xFFF1F0EF))) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    PRTrackerTheme {
        WorkoutEntryBody(workoutUiState = WorkoutUiState(
            WorkoutDetails(
                name = "Item name", reps = "10.00", notes = "5"
            )
        ), onWorkoutValueChange = {}, onSaveClick = {})
    }
}
