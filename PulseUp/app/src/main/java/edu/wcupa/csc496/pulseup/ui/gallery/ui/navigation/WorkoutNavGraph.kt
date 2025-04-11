package edu.wcupa.csc496.pulseup.ui.gallery.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.wcupa.csc496.pulseup.ui.gallery.ui.home.HomeDestination
import edu.wcupa.csc496.pulseup.ui.gallery.ui.home.HomeScreen
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.WorkoutDetailsDestination
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.WorkoutDetailsScreen
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.WorkoutEditDestination
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.WorkoutEditScreen
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.WorkoutEntryDestination
import edu.wcupa.csc496.pulseup.ui.gallery.ui.workout.WorkoutEntryScreen

@Composable
fun WorkoutNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(WorkoutEntryDestination.route) },
                navigateToItemUpdate = {
                    navController.navigate("${WorkoutDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = WorkoutEntryDestination.route) {
            WorkoutEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = WorkoutDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(WorkoutDetailsDestination.workoutIdArg) {
                type = NavType.IntType
            })
        ) {
            WorkoutDetailsScreen(
                navigateToEditItem = { navController.navigate("${WorkoutEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = WorkoutEditDestination.routeWithArgs,
            arguments = listOf(navArgument(WorkoutEditDestination.workoutIdArg) {
                type = NavType.IntType
            })
        ) {
            WorkoutEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
