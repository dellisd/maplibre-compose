package ca.derekellis.maplibre.samples

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ca.derekellis.maplibre.MapLibreMap
import ca.derekellis.maplibre.Navigator
import ca.derekellis.maplibre.Screen
import ca.derekellis.maplibre.layers.BackgroundLayer
import ca.derekellis.maplibre.layers.CircleLayer
import ca.derekellis.maplibre.sources.GeoJsonSource
import ca.derekellis.maplibre.styles.backgroundColor
import ca.derekellis.maplibre.styles.circleRadius
import java.net.URI

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NoStyleSample(navigator: Navigator) {
  Scaffold(topBar = {
    SampleAppBar(title = "No Style Sample", onNavigate = { navigator.goTo(Screen.Home) })
  }) { innerPadding ->
    MapLibreMap(
      modifier = Modifier.consumeWindowInsets(innerPadding),
      style = "",
    ) {
      BackgroundLayer(id = "background") {
        backgroundColor(color = Color.White)
      }
      GeoJsonSource(
        id = "test",
        uri = URI.create("https://raw.githubusercontent.com/georgique/world-geojson/develop/countries/canada.json")
      ) {
        CircleLayer(id = "test") {
          circleRadius(radius = 2f)
        }
      }
    }
  }
}
