package ca.derekellis.maplibre.layers

import ca.derekellis.maplibre.sources.SourceScope
import com.mapbox.mapboxsdk.style.layers.Layer

@LayerDsl
public interface LayerScope<T : Layer> : SourceScope {
  public val layer: T
}
