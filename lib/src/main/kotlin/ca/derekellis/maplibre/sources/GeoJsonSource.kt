package ca.derekellis.maplibre.sources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.remember
import ca.derekellis.maplibre.MapDsl
import ca.derekellis.maplibre.MapScope
import ca.derekellis.maplibre.compose.MapNodeApplier
import ca.derekellis.maplibre.compose.SourceNode
import java.net.URI
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource as SdkGeoJsonSource

@Composable
@MapDsl
public fun MapScope.GeoJsonSource(
  id: String,
  uri: URI,
  layers: @Composable SourceScope.() -> Unit,
) {
  val scope = remember {
    object : SourceScope, MapScope by this {
      override val sourceId: String get() = id
    }
  }

  ComposeNode<SourceNode, MapNodeApplier>(
    factory = { SourceNode(id, SdkGeoJsonSource(id, uri)) },
    update = {
      // TODO: Update ID
      set(uri) {
        style.getSourceAs<SdkGeoJsonSource>(id)?.setUri(uri)
      }
    },
    content = { scope.layers() },
  )
}
