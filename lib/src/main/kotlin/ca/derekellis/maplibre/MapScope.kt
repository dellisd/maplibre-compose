package ca.derekellis.maplibre

import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style

@MapDsl
public interface MapScope {
  public val map: MapboxMap

  public val style: Style
}
