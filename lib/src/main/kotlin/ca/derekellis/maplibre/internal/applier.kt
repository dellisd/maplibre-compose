package ca.derekellis.maplibre.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import ca.derekellis.maplibre.StyleScope
import ca.derekellis.maplibre.compose.MapNodeApplier
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun CoroutineScope.applySources(map: MapboxMap, style: Style, content: @Composable StyleScope.() -> Unit) {
  val recomposer = Recomposer(coroutineContext)
  val composition = Composition(MapNodeApplier(style), recomposer)

  launch {
    recomposer.runRecomposeAndApplyChanges()
  }

  val mapScope = object : StyleScope {
    override val style: Style get() = style
  }

  composition.setContent {
    mapScope.content()
  }
}
