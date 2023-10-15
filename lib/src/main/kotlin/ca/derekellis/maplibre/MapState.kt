package ca.derekellis.maplibre

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnCameraIdleListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

public class MapState(
  private val initialTarget: LatLng = LatLng(),
  private val initialZoom: Double = 0.0,
  private val initialBearing: Double = 0.0,
  private val initialTilt: Double = 0.0,
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
    center: LatLng = this.target,
  ) {
    var listener: OnCameraIdleListener
    var resumed = false
    suspendCancellableCoroutine { continuation ->
      map.easeCamera(CameraUpdateFactory.newLatLng(center))
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
  tilt: Double = 0.0
): MapState {
  return remember { MapState(target, zoom, bearing, tilt) }
}
