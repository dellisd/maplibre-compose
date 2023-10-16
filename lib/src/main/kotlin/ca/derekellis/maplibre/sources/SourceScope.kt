package ca.derekellis.maplibre.sources

import ca.derekellis.maplibre.MapScope

@SourceDsl
public interface SourceScope : MapScope {
  public val sourceId: String
}
