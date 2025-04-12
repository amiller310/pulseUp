@file:OptIn(ExperimentalMaterial3Api::class)

package edu.wcupa.csc496.pulseup.ui.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.wcupa.csc496.pulseup.R.string
import edu.wcupa.csc496.pulseup.ui.gallery.ui.navigation.WorkoutNavHost
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight

@Composable
fun PRApp(navController: NavHostController = rememberNavController()) {
    WorkoutNavHost(navController = navController)
}

@Composable
fun PRTopAppBar(
    title: String,
    isHomeScreen: Boolean,
    isEditScreen: Boolean,
    isEntryScreen: Boolean,
    isDetailsScreen: Boolean,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
) {
    // i added this to check where the issue is coming from, it shows up in logcat now
    println("padding = ${WindowInsets.statusBars.asPaddingValues().calculateTopPadding()}")
    CenterAlignedTopAppBar(
        title = {
            /*
            Instead of nested if, a when statement with an enum would be better here
            E.g. when (someEnumName) {
                isHomeScreen -> Text("Home")
                isEditScreen -> Text("Edit")
                isEntryScreen -> Text("Entry")
                isDetailsScreen -> Text("Details")
            }
             */

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (isHomeScreen) {
                    Text("Home", style = MaterialTheme.typography.titleLarge)
                    Text(
                        "All in one tracker for personal fitness records.",
                        style = TextStyle(fontStyle = FontStyle.Italic, fontSize = 12.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else if (isEditScreen) {
                    Text("Edit", style = MaterialTheme.typography.titleLarge)
                } else if (isEntryScreen) {
                    Text("Entry", style = MaterialTheme.typography.titleLarge)
                } else if (isDetailsScreen) {
                    Text("Details", style = MaterialTheme.typography.titleLarge)
                }
            }
        },
        modifier = modifier.padding(),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFF1F0EF)
        ),
        /// this one line solves your issue
        windowInsets = WindowInsets(top = 0)
    )
}