package ca.derekellis.maplibre

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import ca.derekellis.maplibre.internal.applySources
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style

@Composable
public fun MapLibreMap(
  modifier: Modifier = Modifier,
  style: String,
  state: MapState = rememberMapState(),
  content: @Composable StyleScope.() -> Unit = {},
) {
  var mapRef: MapboxMap? by remember { mutableStateOf(null) }
  var styleRef: Style? by remember { mutableStateOf(null) }

  LaunchedEffect(mapRef, styleRef) {
    if (mapRef != null && styleRef != null) {
      applySources(mapRef!!, styleRef!!, content)
    }
  }

  LaunchedEffect(mapRef, style) {
    val map = mapRef ?: return@LaunchedEffect

    map.setStyle(style)
    map.getStyle { styleRef = it }
  }

  AndroidView(
    modifier = modifier,
    factory = { context ->
      MapView(context).also { view ->
        view.getMapAsync { map ->
          mapRef = map
          state.bindMap(map)
        }
      }
    },
    update = { _ -> }
  )
}
