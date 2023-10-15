package ca.derekellis.maplibre

import com.mapbox.mapboxsdk.maps.Style

@StyleDsl
public interface StyleScope {
  public val style: Style
}
