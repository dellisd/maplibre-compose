package ca.derekellis.maplibre.samples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import ca.derekellis.maplibre.DemoStyle
import ca.derekellis.maplibre.MapLibreMap
import ca.derekellis.maplibre.Navigator
import ca.derekellis.maplibre.Screen
import ca.derekellis.maplibre.layers.CircleLayer
import ca.derekellis.maplibre.rememberMapState
import ca.derekellis.maplibre.sources.GeoJsonSource
import ca.derekellis.maplibre.styles.circleRadius
import java.net.URI

@OptIn(ExperimentalLayoutApi::class)
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
            circleRadius(radius = 2f)
          }
        }
      }
    }
  }
}
