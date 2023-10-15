package ca.derekellis.maplibre.compose

import androidx.compose.runtime.AbstractApplier
import com.mapbox.mapboxsdk.maps.Style

internal class MapNodeApplier(private val style: Style) : AbstractApplier<MapNode>(RootNode()) {

  override fun insertBottomUp(index: Int, instance: MapNode) {
    when (val node = current) {
      is LayerNode -> {
        // TODO
      }

      is SourceNode -> {
        check(instance is LayerNode) { "Node must be a LayerNode" }
//        check(style.getLayer(instance.id) == null) { "Layer with id ${instance.id} already exists in the style" }

        if (style.getLayer(instance.id) == null) {
          style.addLayer(instance.layer)
          node.layers.add(index, instance)
        }
      }

      else -> {}
    }
  }

  override fun insertTopDown(index: Int, instance: MapNode) {
    when (val node = current) {
      is RootNode -> {
        check(instance is SourceNode) { "Node must be a SourceNode" }
//        check(style.getSource(instance.id) == null) { "Source with id ${instance.id} already exists in the style" }

        if (style.getSource(instance.id) == null) {
          style.addSource(instance.source)
          node.children.add(index, instance)
        }
      }

      else -> {}
    }
  }

  override fun move(from: Int, to: Int, count: Int) {
    // TODO: Handle reordering of layers?
  }

  override fun onClear() {
    (root as? RootNode)?.run {
      children.forEach { sourceNode ->
        sourceNode.layers.forEach { layerNode ->
          style.removeLayer(layerNode.id)
        }
        style.removeSource(sourceNode.id)
      }
      children.clear()
    }
  }

  override fun remove(index: Int, count: Int) {
    when (val node = current) {
      is LayerNode -> {
        // TODO
      }

      is SourceNode -> {
        style.removeLayer(node.layers[index].id)
        node.layers.removeAt(index)
      }

      is RootNode -> {
        val source = node.children[index]
        source.layers.forEach { layerNode -> style.removeLayer(layerNode.id) }
        style.removeLayer(source.id)

        node.children.removeAt(index)
      }
    }
  }
}
