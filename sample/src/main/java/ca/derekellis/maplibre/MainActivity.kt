package ca.derekellis.maplibre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ca.derekellis.maplibre.samples.BasicSample
import ca.derekellis.maplibre.samples.NoStyleSample
import ca.derekellis.maplibre.samples.StylesSample
import ca.derekellis.maplibre.ui.theme.MapLibreComposeTheme
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
  private val screenFlow = MutableStateFlow<Screen>(Screen.Home)
  private val navigator: Navigator = object : Navigator {
    override fun goTo(screen: Screen) {
      screenFlow.value = screen
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)

    // Initialize MapLibre
    Mapbox.getInstance(this)

    setContent {
      val screen by screenFlow.collectAsState()

      if (screen !is Screen.Home) {
        BackHandler {
          when (screen) {
            Screen.Home -> { /* No-op */
            }

            else -> navigator.goTo(Screen.Home)
          }
        }
      }

      MapLibreComposeTheme {
        AnimatedContent(targetState = screen, label = "main") { targetScreen ->
          when (targetScreen) {
            Screen.Home -> HomeScreen(navigator)
            Screen.BasicSample -> BasicSample(navigator)
            Screen.StylesSample -> StylesSample(navigator)
            Screen.NoStylesSample -> NoStyleSample(navigator)
          }
        }
      }
    }
  }
}
