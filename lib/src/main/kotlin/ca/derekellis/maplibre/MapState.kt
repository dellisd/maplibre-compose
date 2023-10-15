package ca.derekellis.maplibre

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdate
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnCameraIdleListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

public class MapState(
  private val initialTarget: LatLng = LatLng(),
  private val initialZoom: Double = 0.0,
  private val initialBearing: Double = 0.0,
  private val initialTilt: Double = 0.0,
  private val initialPadding: DoubleArray = doubleArrayOf(0.0, 0.0, 0.0, 0.0)
) {
  private var _map: MapboxMap? = null
    get() {
      checkNotNull(field) { "State has not been bound to a map! Did you remember to pass the state into a MapLibreMap?" }
      return field
    }
  private val map get(): MapboxMap = _map!!

  internal fun bindMap(map: MapboxMap) {
    this._map = map
    map.cameraPosition = CameraPosition.Builder()
      .target(initialTarget)
      .zoom(initialZoom)
      .bearing(initialBearing)
      .tilt(initialTilt)
      .padding(initialPadding)
      .build()
  }

  public var target: LatLng
    get() = map.cameraPosition.target ?: LatLng()
    set(value) {
      map.cameraPosition = CameraPosition.Builder().target(value).build()
    }

  public var zoom: Double
    get() = map.cameraPosition.zoom
    set(value) {
      map.cameraPosition = CameraPosition.Builder().zoom(value).build()
    }

  public var bearing: Double
    get() = map.cameraPosition.bearing
    set(value) {
      map.cameraPosition = CameraPosition.Builder().bearing(value).build()
    }

  public var tilt: Double
    get() = map.cameraPosition.tilt
    set(value) {
      map.cameraPosition = CameraPosition.Builder().tilt(value).build()
    }

  public suspend fun easeTo(
    target: LatLng = this.target,
    zoom: Double = this.zoom,
    bearing: Double = this.bearing,
    tilt: Double = this.tilt,
    duration: Duration = 300.milliseconds,
  ): Unit = doAnimation {
    val cameraUpdate = object : CameraUpdate {
      override fun getCameraPosition(mapboxMap: MapboxMap): CameraPosition =
        CameraPosition.Builder()
          .target(target)
          .zoom(zoom)
          .bearing(bearing)
          .tilt(tilt)
          .build()
    }

    map.easeCamera(cameraUpdate, duration.inWholeMilliseconds.toInt())
  }

  private suspend fun doAnimation(block: () -> Unit) {
    var listener: OnCameraIdleListener
    var resumed = false

    suspendCancellableCoroutine { continuation ->
      block()
      listener = OnCameraIdleListener {
        if (!resumed) {
          continuation.resume(Unit)
        }
        resumed = true
      }

      map.addOnCameraIdleListener(listener)

      continuation.invokeOnCancellation {
        map.cancelTransitions()
        map.removeOnCameraIdleListener(listener)
      }
    }
  }
}

@Composable
public fun rememberMapState(
  target: LatLng = LatLng(),
  zoom: Double = 0.0,
  bearing: Double = 0.0,
  tilt: Double = 0.0,
  padding: PaddingValues = PaddingValues(0.dp),
): MapState {
  val paddingValues = LocalDensity.current.run {
     doubleArrayOf(
      padding.calculateLeftPadding(LayoutDirection.Ltr).toPx().toDouble(),
      padding.calculateTopPadding().toPx().toDouble(),
      padding.calculateRightPadding(LayoutDirection.Ltr).toPx().toDouble(),
      padding.calculateBottomPadding().toPx().toDouble(),
    )
  }

  return remember { MapState(target, zoom, bearing, tilt, paddingValues) }
}
