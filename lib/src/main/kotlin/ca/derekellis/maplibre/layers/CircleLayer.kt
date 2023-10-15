package ca.derekellis.maplibre.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import ca.derekellis.maplibre.compose.LayerNode
import ca.derekellis.maplibre.compose.MapNodeApplier
import ca.derekellis.maplibre.sources.SourceScope
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.CircleLayer as SdkCircleLayer

@Composable
public fun SourceScope.CircleLayer(id: String) {
  ComposeNode<LayerNode, MapNodeApplier>(
    factory = {
      val layer = SdkCircleLayer(id, sourceId).withProperties(
        PropertyFactory.circleRadius(5f)
      )
      LayerNode(id, layer)
    },
    update = {
      // TODO
    }
  )
}
