package ca.derekellis.maplibre.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.remember
import ca.derekellis.maplibre.StyleScope
import ca.derekellis.maplibre.compose.LayerNode
import ca.derekellis.maplibre.compose.MapNodeApplier
import ca.derekellis.maplibre.sources.SourceDsl
import com.mapbox.mapboxsdk.style.layers.BackgroundLayer as SdkBackgroundLayer

@Composable
@SourceDsl
public fun StyleScope.BackgroundLayer(
  id: String,
  styles: @Composable LayerScope<SdkBackgroundLayer>.() -> Unit = {},
) {
  val layer = remember { SdkBackgroundLayer(id) }
  val scope = remember {
    object : LayerScope<SdkBackgroundLayer>, StyleScope by this {
      override val layer: SdkBackgroundLayer get() = layer
    }
  }

  ComposeNode<LayerNode, MapNodeApplier>(
    factory = { LayerNode(id, layer) },
    update = {
      // TODO
    },
    content = { scope.styles() }
  )
}
