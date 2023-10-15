package ca.derekellis.maplibre.samples

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.derekellis.maplibre.DemoStyle
import ca.derekellis.maplibre.MapLibreMap
import ca.derekellis.maplibre.Navigator
import ca.derekellis.maplibre.Screen
import ca.derekellis.maplibre.layers.CircleLayer
import ca.derekellis.maplibre.rememberMapState
import ca.derekellis.maplibre.sources.GeoJsonSource
import ca.derekellis.maplibre.styles.circleRadius
import java.net.URI
import kotlin.math.round

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StylesSample(navigator: Navigator) {
  Scaffold(topBar = {
    Row(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
      IconButton(onClick = { navigator.goTo(Screen.Home) }) {
        Icon(Icons.Default.Close, contentDescription = "Close")
      }
    }
  }) { innerPadding ->
    val scope = rememberCoroutineScope()

    val mapState = rememberMapState(padding = innerPadding)
    var showSettings by remember { mutableStateOf(false) }
    var radiusSetting by remember { mutableFloatStateOf(5f) }

    if (showSettings) {
      BackHandler {
        showSettings = false
      }
    }

    Box(
      modifier = Modifier.consumeWindowInsets(innerPadding),
    ) {
      MapLibreMap(
        modifier = Modifier.fillMaxSize(),
        style = DemoStyle.Default.url,
        state = mapState,
      ) {
        GeoJsonSource(
          id = "sample",
          uri = URI.create("https://raw.githubusercontent.com/georgique/world-geojson/develop/countries/canada.json")
        ) {
          CircleLayer(id = "circles") {
            circleRadius(radius = radiusSetting)
          }
        }
      }
      Column(
        modifier = Modifier
          .fillMaxSize()
          .consumeWindowInsets(PaddingValues(16.dp))
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.End,
      ) {
        AnimatedVisibility(visible = showSettings) {
          Surface(
            modifier = Modifier
              .fillMaxWidth()
              .wrapContentHeight(),
            shadowElevation = 1.dp,
            shape = RoundedCornerShape(16.dp)
          ) {
            Column {
              SliderControl(
                label = "Circle Radius",
                value = radiusSetting,
                range = 1f..10f,
                onChange = { radiusSetting = round(it) })
            }
          }
        }
        ExtendedFloatingActionButton(
          onClick = { showSettings = !showSettings },
          icon = { Icon(Icons.Default.List, contentDescription = "Open Settings") },
          text = { Text(text = "Settings") }
        )
      }
    }
  }
}

@Composable
private fun SliderControl(
  label: String,
  value: Float,
  range: ClosedFloatingPointRange<Float>,
  onChange: (Float) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp, vertical = 16.dp)
  ) {
    Text(text = "$label: $value")
    Slider(value = value, valueRange = range, onValueChange = onChange)
  }
}
