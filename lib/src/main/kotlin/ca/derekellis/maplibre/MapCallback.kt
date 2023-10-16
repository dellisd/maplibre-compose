package ca.derekellis.maplibre

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.mapbox.mapboxsdk.geometry.LatLng

@Composable
public fun MapScope.MapCallback(
  onCameraIdle: (() -> Unit)? = null,
  onCameraMove: (() -> Unit)? = null,
  onCameraMoveCancel: (() -> Unit)? = null,
  onMapClick: ((LatLng) -> Boolean)? = null,
) {
  DisposableEffect(onCameraIdle) {
    onCameraIdle ?: return@DisposableEffect onDispose { }
    map.addOnCameraIdleListener(onCameraIdle)

    onDispose {
      map.removeOnCameraIdleListener(onCameraIdle)
    }
  }

  DisposableEffect(onCameraMove) {
    onCameraMove ?: return@DisposableEffect onDispose { }
    map.addOnCameraMoveListener(onCameraMove)

    onDispose {
      map.removeOnCameraMoveListener(onCameraMove)
    }
  }

  DisposableEffect(onCameraMoveCancel) {
    onCameraMoveCancel ?: return@DisposableEffect onDispose { }
    map.addOnCameraMoveCancelListener(onCameraMoveCancel)

    onDispose {
      map.removeOnCameraMoveCancelListener(onCameraMoveCancel)
    }
  }

  DisposableEffect(onMapClick) {
    onMapClick ?: return@DisposableEffect onDispose { }
    map.addOnMapClickListener(onMapClick)

    onDispose {
      map.removeOnMapClickListener(onMapClick)
    }
  }
}
