package ca.derekellis.maplibre.layers

import ca.derekellis.maplibre.StyleScope
import com.mapbox.mapboxsdk.style.layers.Layer

@LayerDsl
public interface LayerScope<T : Layer> : StyleScope {
  public val layer: T
}
