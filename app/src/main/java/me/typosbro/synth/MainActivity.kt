package me.typosbro.synth

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.typosbro.synth.ui.theme.SynthTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<SynthViewModel>()
    val synth = LoggingWavetableSynth()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        viewModel.wavetableSynth = synth
        setContent {
            SynthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    SynthApp(viewModel = viewModel, modifier = Modifier)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.applyParameters()
    }
}


@Composable
fun SynthApp(viewModel: SynthViewModel, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        SelectionPanel(viewModel, modifier)
        ControlsPanel(viewModel, modifier)
    }
}


@Composable
fun SelectionPanel(
    viewModel: SynthViewModel, modifier: Modifier = Modifier
) {
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
            modifier = modifier.fillMaxSize()
        ) {
            Text(text = stringResource(id = R.string.app_name))
            SelectionButtons(viewModel, modifier)
        }
    }
}

@Composable
fun SelectionButtons(
    viewModel: SynthViewModel, modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier.fillMaxWidth()
    ) {
        for (wavetable in Wavetable.entries) {
            WavetableButton(
                label = stringResource(id = wavetable.toResourceString()),
                onClick = { viewModel.setWavetable(wavetable) },
                modifier
            )
        }
    }
}

@Composable
fun WavetableButton(
    label: String, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = label)
    }
}


@Composable
fun ControlsPanel(
    viewModel: SynthViewModel, modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f)
        ) {
            PitchControl(viewModel, modifier)
            PlayControl(viewModel, modifier)
        }

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            VolumeControl(viewModel = viewModel, modifier = modifier)
        }
    }
}

@Composable
fun PitchControl(viewModel: SynthViewModel, modifier: Modifier) {

    // if the frequency changes, recompose this composable
    val frequency by viewModel.frequency.observeAsState()
    // the slider position state is hoisted by this composable; no need to embed it into
    // the ViewModel, which ideally, shouldn't be aware of the UI.
    // When the slider position changes, this composable will be recomposed as we explained in
    // the UI tutorial.
    var sliderPosition by rememberSaveable {
        mutableFloatStateOf(
            // we use the ViewModel's convenience function to get the initial slider position
            viewModel.getPositionFromFrequencyInHz(frequency!!)
        )
    }

    PitchControlUi(
        label = stringResource(id = R.string.frequency),
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
            viewModel.setFrequency(it)
        },
        valueRange = 0f..1f,
        frequencyValueLabel = stringResource(id = R.string.frequency_value, frequency!!),
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
        value = value, onValueChange = onValueChange, valueRange = valueRange, modifier = modifier
    )
    Text(text = frequencyValueLabel)
}


@Composable
fun PlayControl(viewModel: SynthViewModel, modifier: Modifier) {
    val label by viewModel.playButtonLabel.observeAsState()
    Button(
        onClick = viewModel::onPlayButtonClicked, modifier = modifier
    ) {
        Text(text = stringResource(id = label!!))
    }
}

@Composable
fun VolumeControl(
    viewModel: SynthViewModel,
    modifier: Modifier = Modifier,
) {
    val volume by viewModel.volume.observeAsState()

    VolumeControlUi(
        value = volume!!,
        onValueChange = { viewModel.setVolume(it) },
        valueRange = viewModel.volumeRange,
        modifier = modifier
    )

}

@Composable
fun VolumeControlUi(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sliderHeight = screenHeight / 4

    Icon(imageVector = Icons.Filled.VolumeUp, contentDescription = null)
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        modifier = modifier
            .width(sliderHeight)
            .rotate(270f)
    )
    Icon(imageVector = Icons.Filled.VolumeMute, contentDescription = null)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SynthTheme {
        val viewModel = SynthViewModel()
        SynthApp(viewModel)
    }
}