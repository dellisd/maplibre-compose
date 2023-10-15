package ca.derekellis.maplibre.compose

import androidx.compose.runtime.AbstractApplier
import com.mapbox.mapboxsdk.maps.Style

internal class MapNodeApplier(private val style: Style) : AbstractApplier<MapNode>(RootNode()) {

  override fun insertBottomUp(index: Int, instance: MapNode) {
    when (val node = current) {
      is LayerNode -> {
      }

      is SourceNode -> {
        check(instance is LayerNode) { "Node must be a LayerNode" }
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
        if (instance is SourceNode) {
          if (style.getSource(instance.id) == null) {
            style.addSource(instance.source)
            node.children.add(index, instance)
          }
        } else if (instance is LayerNode) {
          if (style.getLayer(instance.id) == null) {
            style.addLayer(instance.layer)
            node.children.add(index, instance)
          }
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
      children.forEach { node ->
        if (node is SourceNode) {
          node.layers.forEach { layerNode ->
            style.removeLayer(layerNode.id)
          }
          style.removeSource(node.id)
        } else if (node is LayerNode) {
          style.removeLayer(node.id)
        }
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
        val child = node.children[index]
        if (child is SourceNode) {
          child.layers.forEach { layerNode -> style.removeLayer(layerNode.id) }
          style.removeSource(child.id)
        } else if (child is LayerNode) {
          style.removeLayer(child.id)
        }
        node.children.removeAt(index)
      }
    }
  }
}
