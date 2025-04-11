package edu.wcupa.csc496.pulseup.ui.gallery.ui.workout

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.xr.compose.testing.toDp
import edu.wcupa.csc496.pulseup.ui.gallery.PRTopAppBar
import edu.wcupa.csc496.pulseup.R
import edu.wcupa.csc496.pulseup.ui.gallery.data.Workout
import edu.wcupa.csc496.pulseup.ui.gallery.ui.navigation.NavigationDestination
import edu.wcupa.csc496.pulseup.ui.gallery.ui.AppViewModelProvider

object WorkoutDetailsDestination : NavigationDestination {
    override val route = "workout_details"
    override val titleRes = R.string.workout_detail_title
    const val workoutIdArg = "workoutId"
    val routeWithArgs = "$route/{$workoutIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailsScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            PRTopAppBar(
                title = stringResource(WorkoutDetailsDestination.titleRes),
                isHomeScreen = false,
                isEditScreen = false,
                isEntryScreen = false,
                isDetailsScreen = true,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditItem(uiState.value.workoutDetails.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_workout_title),
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        WorkoutDetailsBody(
            workoutDetailsUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteWorkout()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun WorkoutDetailsBody(
    workoutDetailsUiState: WorkoutDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.dumbbellseamlesspattern),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

            WorkoutDetails(
                workout = workoutDetailsUiState.workoutDetails.toWorkout(),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { deleteConfirmationRequired = true },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.delete))
            }
            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDelete()
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }
    }
}

@Composable
fun WorkoutDetails(
    workout: Workout, modifier: Modifier = Modifier
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ) {
            WorkoutDetailsRow(
                labelResID = R.string.item,
                workoutDetail = workout.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            WorkoutDetailsRow(
                labelResID = R.string.reps,
                workoutDetail = workout.formatedReps(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            WorkoutNotesRow(
                labelResID = R.string.notes,
                workoutDetail = workout.notes.toString(),
                expanded = expanded,
                changeExpanded = { expanded = !expanded },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
private fun WorkoutDetailsRow(
    @StringRes labelResID: Int, workoutDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = workoutDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun WorkoutNotesRow(
    @StringRes labelResID: Int, workoutDetail: String, expanded: Boolean, changeExpanded: () -> Unit, modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    val textLayoutResult = textMeasurer.measure(text = workoutDetail)

    val calculatedHeight = textLayoutResult.size.height.toDp()

    val extraPadding by animateDpAsState(
        if (expanded) calculatedHeight else 0.dp
    )

    Column() {
        Row(modifier = modifier.padding(bottom = extraPadding)) {
            Text(stringResource(labelResID))
            Spacer(modifier = Modifier.weight(1f))
            if (workoutDetail != ""){
                ElevatedButton(
                    onClick = { changeExpanded() }
                ) {
                    Text(if (expanded) "Hide notes" else "Show notes")
                }
            } else
                Text(text = "No notes", fontWeight = FontWeight.Bold)
        }
        Row(modifier = modifier) {
            if (expanded) {
                Text(text = workoutDetail, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}


