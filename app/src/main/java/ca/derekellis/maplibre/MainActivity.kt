package ca.derekellis.maplibre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.derekellis.maplibre.DemoStyle.Default
import ca.derekellis.maplibre.DemoStyle.OpenStreetMap
import ca.derekellis.maplibre.layers.CircleLayer
import ca.derekellis.maplibre.sources.GeoJsonSource
import ca.derekellis.maplibre.ui.theme.MapLibreComposeTheme
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import kotlinx.coroutines.launch
import java.net.URI

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Initialize MapLibre
    Mapbox.getInstance(this)

    setContent {
      val scope = rememberCoroutineScope()
      var style: DemoStyle by remember { mutableStateOf(Default) }
      val mapState = rememberMapState()

      MapLibreComposeTheme {
        Box(modifier = Modifier.fillMaxSize()) {
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
                CircleLayer(id = "test")
              }
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
          ) {
            Button(onClick = {
              style = when (style) {
                Default -> OpenStreetMap
                OpenStreetMap -> Default
              }
            }) {
              Text(text = "Toggle Style")
            }
            Button(onClick = {
              scope.launch {
                mapState.easeTo(center = LatLng(47.2639603,11.4002649))
              }
            }) {
              Text(text = "Jump To")
            }
          }
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  MapLibreComposeTheme {
    Greeting("Android")
  }
}