package ca.derekellis.maplibre

import android.view.View
import android.view.View.OnAttachStateChangeListener
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
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
  contentPadding: PaddingValues = WindowInsets.safeDrawing.asPaddingValues(),
  logoPadding: PaddingValues = computeLogoPadding(contentPadding),
  attributionPadding: PaddingValues = computeAttributionPadding(logoPadding),
  compassPadding: PaddingValues = computeCompassPadding(contentPadding),
  content: @Composable StyleScope.() -> Unit = {},
) {
  val density = LocalDensity.current
  val layoutDirection = LocalLayoutDirection.current

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

  LaunchedEffect(mapRef, logoPadding, attributionPadding, compassPadding) {
    val map = mapRef ?: return@LaunchedEffect

    with(density) {
      map.uiSettings.setLogoMargins(
        logoPadding.calculateLeftPadding(layoutDirection).roundToPx(),
        logoPadding.calculateTopPadding().roundToPx(),
        logoPadding.calculateRightPadding(layoutDirection).roundToPx(),
        logoPadding.calculateBottomPadding().roundToPx(),
      )

      map.uiSettings.setAttributionMargins(
        attributionPadding.calculateLeftPadding(layoutDirection).roundToPx(),
        attributionPadding.calculateTopPadding().roundToPx(),
        attributionPadding.calculateRightPadding(layoutDirection).roundToPx(),
        attributionPadding.calculateBottomPadding().roundToPx(),
      )

      map.uiSettings.setCompassMargins(
        compassPadding.calculateLeftPadding(layoutDirection).roundToPx(),
        compassPadding.calculateTopPadding().roundToPx(),
        compassPadding.calculateRightPadding(layoutDirection).roundToPx(),
        compassPadding.calculateBottomPadding().roundToPx(),
      )
    }
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
      Lifecycle.Event.ON_ANY -> {
        /* No-op */
      }
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

@Composable
public fun computeLogoPadding(contentPadding: PaddingValues): PaddingValues {
  return PaddingValues(
    start = 4.dp + contentPadding.calculateStartPadding(LocalLayoutDirection.current),
    top = 4.dp + contentPadding.calculateTopPadding(),
    end = 4.dp + contentPadding.calculateEndPadding(LocalLayoutDirection.current),
    bottom = 4.dp + contentPadding.calculateBottomPadding(),
  )
}

@Composable
public fun computeAttributionPadding(logoPadding: PaddingValues): PaddingValues {
  return PaddingValues(
    start = 92.dp + logoPadding.calculateStartPadding(LocalLayoutDirection.current),
    top = 4.dp + logoPadding.calculateTopPadding(),
    end = 4.dp + logoPadding.calculateEndPadding(LocalLayoutDirection.current),
    bottom = 4.dp + logoPadding.calculateBottomPadding(),
  )
}

@Composable
public fun computeCompassPadding(contentPadding: PaddingValues): PaddingValues {
  return PaddingValues(
    start = 4.dp + contentPadding.calculateStartPadding(LocalLayoutDirection.current),
    top = 4.dp + contentPadding.calculateTopPadding(),
    end = 4.dp + contentPadding.calculateEndPadding(LocalLayoutDirection.current),
    bottom = 4.dp + contentPadding.calculateBottomPadding(),
  )
}
