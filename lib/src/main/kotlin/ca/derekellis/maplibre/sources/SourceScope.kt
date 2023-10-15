package ca.derekellis.maplibre.sources

import ca.derekellis.maplibre.StyleScope

public interface SourceScope : StyleScope {
  public val sourceId: String
}