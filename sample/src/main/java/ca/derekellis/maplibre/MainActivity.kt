package ca.derekellis.maplibre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ca.derekellis.maplibre.samples.BasicSample
import ca.derekellis.maplibre.ui.theme.MapLibreComposeTheme
import com.mapbox.mapboxsdk.Mapbox

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    // Initialize MapLibre
    Mapbox.getInstance(this)

    setContent {
      MapLibreComposeTheme {
        BasicSample()
      }
    }
  }
}
