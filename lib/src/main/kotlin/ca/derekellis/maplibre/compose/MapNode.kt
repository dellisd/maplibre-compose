package ca.derekellis.maplibre.compose

import com.mapbox.mapboxsdk.style.layers.Layer
import com.mapbox.mapboxsdk.style.sources.Source

internal sealed interface MapNode

internal class RootNode : MapNode {
  val children = mutableListOf<MapNode>()
}

internal class SourceNode(var id: String, var source: Source) : MapNode {
  val layers: MutableList<LayerNode> = mutableListOf()
}

internal class LayerNode(var id: String, var layer: Layer) : MapNode
