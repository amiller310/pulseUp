package edu.wcupa.csc496.pulseup.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.wcupa.csc496.pulseup.databinding.FragmentHomeBinding
import edu.wcupa.csc496.pulseup.ui.theme.PulseUpTheme

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                PulseUpTheme {
                    MyApp()
                }
            }
        }
    }

    @Composable
    fun MyApp(modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier/*, names: List<String> = listOf("World", "Compose")*/) {
        var shouldShowOnboarding by remember { mutableStateOf(true) }

        Surface(modifier) {
            if (shouldShowOnboarding) {
                OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
            } else {
                Greetings()
            }
        }
        /*Column(modifier = modifier.padding(vertical = 4.dp)){
    for (name in names) {
        Greeting(name = name)
    }*/
    }

    @Composable
    fun OnboardingScreen(
        onContinueClicked: () -> Unit,
        modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
    ) {
        //var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to PulseUp!")
            Text("What would you like to do with your body?")
            Button(
                modifier = androidx.compose.ui.Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
                    Text("IM TRYNA GET SWOLEEEEEE!")
                }

            }

            Button(
                modifier = androidx.compose.ui.Modifier.padding(
                    vertical = 24.dp,
                    horizontal = 24.dp
                ),
                onClick = onContinueClicked
            ) {
                Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
                    Text("I WANT TO BE A LEAN, GREEN, FIGHTING MACHINEEEE!")
                }

            }
        }
    }

    @Composable
    private fun Greetings(
        modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
        workoutNames: List<String> = listOf(
            "Workout 1",
            "Workout 2",
            "Workout 3 ",

            "Workout 1",
            "Workout 2",
            "Workout 3 ",

            "Workout 1",
            "Workout 2",
            "Workout 3 ",
        )
    ) {
        Text("So you wanna be SWOLEE, huh!? Let's see how you do with this >:)")
        LazyColumn(modifier = modifier.padding(vertical = 60.dp)) {
            items(items = workoutNames) { name ->
                Greeting(name = name)
            }
            /* val expanded = remember {mutableStateOf(false)}
        val extraPadding = if (expanded.value) 48.dp else 0.dp
        Surface(color = MaterialTheme.colorScheme.primary, modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
            Row(modifier = Modifier.padding(24.dp)){
                Column(modifier = modifier.weight(1f).padding(bottom = extraPadding)){
                    Text (text = "Hello")
                    Text (text = name)
                }
                ElevatedButton(
                    onClick = {expanded.value = !expanded.value}
                ){
                    Text(if(expanded.value) "Show less" else "Show more")
            }
        }
    }*/
        }
    }

    @Preview(showBackground = true, widthDp = 320, heightDp = 320)
    @Composable
    fun OnboardingPreview() {
        PulseUpTheme {
            OnboardingScreen(onContinueClicked = {})
        }
    }

    @Composable
    fun Greeting(
        name: String,
        modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            CardContent(name)
        }

    }

    @Composable
    private fun CardContent(name: String) {
        var expanded by rememberSaveable { mutableStateOf(false) }

        Row(
            modifier = androidx.compose.ui.Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = androidx.compose.ui.Modifier.weight(1f).padding(12.dp)
            ) {
                Text(
                    text = name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy.").repeat(4)
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        "Show More"
                    } else {
                        "Show Less"
                    }
                )
            }
        }
    }
}

//@Preview(
//    showBackground = true,
//    widthDp = 320,
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    name = "GreetingPreviewDark"
//)
//@Preview(showBackground = true, widthDp = 320)
//@Composable
//fun GreetingPreview() {
//    PulseUpTheme {
//        Greetings()
//    }
//}
//
//@Preview
//@Composable
//fun MyAppPReview() {
//    PulseUpTheme {
//        MyApp(androidx.compose.ui.Modifier.fillMaxSize())
//    }
//}
