package ca.derekellis.maplibre.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.remember
import ca.derekellis.maplibre.compose.LayerNode
import ca.derekellis.maplibre.compose.MapNodeApplier
import ca.derekellis.maplibre.sources.SourceDsl
import ca.derekellis.maplibre.sources.SourceScope
import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.CircleLayer as SdkCircleLayer

@Composable
@SourceDsl
public fun SourceScope.CircleLayer(
  id: String,
  styles: @Composable LayerScope<SdkCircleLayer>.() -> Unit = {},
) {
  val layer = remember { SdkCircleLayer(id, sourceId) }
  val scope = remember {
    object : LayerScope<SdkCircleLayer>, SourceScope by this {
      override val layer: CircleLayer get() = layer
    }
  }

  ComposeNode<LayerNode, MapNodeApplier>(
    factory = { LayerNode(id, layer) },
    update = {
      // TODO
    },
    content = { scope.styles() },
  )
}
