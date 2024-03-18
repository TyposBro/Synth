package me.typosbro.synth

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.typosbro.synth.ui.theme.SynthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContent {
            SynthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SynthApp(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun SynthApp(modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize(),
    )
    {

        SelectionPanel(modifier)
        ControlsPanel(modifier)
    }
}


@Composable
fun SelectionPanel(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxSize()
        ) {
            Text(text = stringResource(id = R.string.app_name))
            SelectionButtons(modifier)
        }
    }
}

@Composable
fun SelectionButtons(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
    ) {
        for (wavetable in arrayOf("Sine", "Saw", "Square", "Triangle")) {
            WavetableButton(label = wavetable, onClick = { /*TODO*/ }, modifier)
        }
    }
}

@Composable
fun WavetableButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = label)
    }
}


@Composable
fun ControlsPanel(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
        ) {
            PitchControl(modifier)
            PlayControl(modifier)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

        }
    }
}

@Composable
fun PitchControl(modifier: Modifier) {

    var frequency by rememberSaveable { mutableFloatStateOf(300f) }

    PitchControlUi(
        label = stringResource(id = R.string.frequency),
        value = frequency,
        onValueChange = { frequency = it },
        valueRange = 40f..3000f,
        frequencyValueLabel = stringResource(id = R.string.frequency_value, frequency),
        modifier = modifier
    )
}

@Composable
fun PitchControlUi(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    frequencyValueLabel: String,
    modifier: Modifier
) {
    Text(text = label)
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        modifier = modifier
    )
    Text(text = frequencyValueLabel)
}


@Composable
fun PlayControl(modifier: Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.play))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SynthTheme {
        SynthApp()
    }
}