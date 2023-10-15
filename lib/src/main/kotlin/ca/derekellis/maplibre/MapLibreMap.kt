package ca.derekellis.maplibre

import android.view.View
import android.view.View.OnAttachStateChangeListener
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.findViewTreeLifecycleOwner
import ca.derekellis.maplibre.compose.applySources
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style

@Composable
public fun MapLibreMap(
  modifier: Modifier = Modifier,
  style: String? = null,
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
      }.apply { manageLifecycle() }
    },
    update = { _ -> },
  )
}

private fun MapView.manageLifecycle() {
  val observer = LifecycleEventObserver { _, event ->
    when (event) {
      Lifecycle.Event.ON_CREATE -> onCreate(null)
      Lifecycle.Event.ON_START -> onStart()
      Lifecycle.Event.ON_RESUME -> onResume()
      Lifecycle.Event.ON_PAUSE -> onPause()
      Lifecycle.Event.ON_STOP -> onStop()
      Lifecycle.Event.ON_DESTROY -> onDestroy()
      Lifecycle.Event.ON_ANY -> { /* No-op */ }
    }
  }

  addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(v: View) {
      val owner = requireNotNull(v.findViewTreeLifecycleOwner())
      owner.lifecycle.addObserver(observer)
    }

    override fun onViewDetachedFromWindow(v: View) {
      val owner = requireNotNull(v.findViewTreeLifecycleOwner())
      owner.lifecycle.removeObserver(observer)
    }
  })
}
