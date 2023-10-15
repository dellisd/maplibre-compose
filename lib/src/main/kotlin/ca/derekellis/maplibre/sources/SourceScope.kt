package ca.derekellis.maplibre.sources

import ca.derekellis.maplibre.StyleScope

@SourceDsl
public interface SourceScope : StyleScope {
  public val sourceId: String
}
