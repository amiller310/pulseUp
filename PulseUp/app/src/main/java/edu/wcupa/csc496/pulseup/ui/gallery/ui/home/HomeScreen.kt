package edu.wcupa.csc496.pulseup.ui.gallery.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.wcupa.csc496.pulseup.ui.gallery.PRTopAppBar
import edu.wcupa.csc496.pulseup.R
import edu.wcupa.csc496.pulseup.ui.gallery.data.Workout
import edu.wcupa.csc496.pulseup.ui.gallery.ui.AppViewModelProvider
import edu.wcupa.csc496.pulseup.ui.gallery.ui.navigation.NavigationDestination
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.formatedReps


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()
    var showSortMenu by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PRTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                isHomeScreen = true,
                isEditScreen = false,
                isEntryScreen = false,
                isDetailsScreen = false,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            var highlightChoice by remember { mutableStateOf(0) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box {
                        FloatingActionButton(
                            onClick = { showSortMenu = true },
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sort),
                                contentDescription = "Sort",
                            )
                        }
                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false },
                        ) {
                            DropdownMenuItem(
                                text = { Text("Name (A-Z)") },
                                onClick = {
                                    viewModel.updateSortOption(SortOption.NAME_ASC)
                                    showSortMenu = false
                                    highlightChoice = 0
                                },
                                modifier = Modifier.background(if (highlightChoice == 0) Color.LightGray else Color.Transparent)
                            )
                            DropdownMenuItem(
                                text = { Text("Name (Z-A)") },
                                onClick = {
                                    viewModel.updateSortOption(SortOption.NAME_DESC)
                                    showSortMenu = false
                                    highlightChoice = 1
                                },
                                modifier = Modifier.background(if (highlightChoice == 1) Color.LightGray else Color.Transparent)
                            )
                            DropdownMenuItem(
                                text = { Text("Reps (Low to High)") },
                                onClick = {
                                    viewModel.updateSortOption(SortOption.REPS_ASC)
                                    showSortMenu = false
                                    highlightChoice = 2
                                },
                                modifier = Modifier.background(if (highlightChoice == 2) Color.LightGray else Color.Transparent)
                            )
                            DropdownMenuItem(
                                text = { Text("Reps (High to Low)") },
                                onClick = {
                                    viewModel.updateSortOption(SortOption.REPS_DESC)
                                    showSortMenu = false
                                    highlightChoice = 3
                                },
                                modifier = Modifier.background(if (highlightChoice == 3) Color.LightGray else Color.Transparent)
                            )
                        }
                    }
                    FloatingActionButton(
                        onClick = navigateToItemEntry,
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.workout_entry_title)
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        HomeBody(
            itemList = homeUiState.workoutList,
            onItemClick = navigateToItemUpdate,
            modifier = modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun HomeBody(
    itemList: List<Workout>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxWidth(),
        ) {
            if (itemList.isEmpty()) {
                Box (modifier = modifier.background(Color(0xFFF1F0EF))) {
                    Text(
                        text = stringResource(R.string.no_workout_description),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(contentPadding),
                    )
                }
            } else {
                InventoryList(
                    itemList = itemList,
                    onWorkoutClick = { onItemClick(it.id) },
                    contentPadding = contentPadding,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
private fun InventoryList(
    itemList: List<Workout>,
    onWorkoutClick: (Workout) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.id }) { workout ->
            InventoryWorkout(workout = workout,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onWorkoutClick(workout) })
        }
    }
}

@Composable
private fun InventoryWorkout(
    workout: Workout, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Text(
                text = workout.formatedReps(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}