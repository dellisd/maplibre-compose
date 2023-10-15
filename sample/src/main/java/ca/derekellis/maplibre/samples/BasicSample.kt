package ca.derekellis.maplibre.samples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ca.derekellis.maplibre.DemoStyle
import ca.derekellis.maplibre.MapLibreMap
import ca.derekellis.maplibre.Navigator
import ca.derekellis.maplibre.Screen
import ca.derekellis.maplibre.layers.CircleLayer
import ca.derekellis.maplibre.rememberMapState
import ca.derekellis.maplibre.sources.GeoJsonSource
import ca.derekellis.maplibre.styles.circleRadius
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.coroutines.launch
import java.net.URI
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BasicSample(navigator: Navigator) {
  Scaffold(topBar = {
    SampleAppBar(title = "Basic Sample", onNavigate = { navigator.goTo(Screen.Home) })
  }) { innerPadding ->
    val scope = rememberCoroutineScope()

    var style: DemoStyle by remember { mutableStateOf(DemoStyle.Default) }
    val mapState = rememberMapState(padding = innerPadding)

    Box(
      modifier = Modifier.consumeWindowInsets(innerPadding),
      contentAlignment = Alignment.BottomCenter
    ) {
      // A surface container using the 'background' color from the theme
      Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        MapLibreMap(
          modifier = Modifier.fillMaxSize(),
          style = style.url,
          state = mapState,
        ) {
          GeoJsonSource(
            id = "test",
            uri = URI.create("https://raw.githubusercontent.com/RailFansCanada/RailFansMap/master/data/ottawa/line1.json")
          ) {
            CircleLayer(id = "test") {
              circleRadius(radius = 10f)
            }
          }
        }
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.SpaceAround
      ) {
        Button(onClick = {
          style = when (style) {
            DemoStyle.Default -> DemoStyle.OpenStreetMap
            DemoStyle.OpenStreetMap -> DemoStyle.Default
          }
        }) {
          Text(text = "Toggle Style")
        }
        Button(onClick = {
          scope.launch {
            mapState.easeTo(
              target = LatLng(47.2639603, 11.4002649),
              zoom = 15.0,
              duration = 1.seconds
            )
          }
        }) {
          Text(text = "Jump To")
        }
      }
    }
  }
}
