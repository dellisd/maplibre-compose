package ca.derekellis.maplibre.compose

import androidx.compose.runtime.AbstractApplier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import ca.derekellis.maplibre.StyleScope
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

internal fun CoroutineScope.applySources(map: MapboxMap, style: Style, content: @Composable StyleScope.() -> Unit) {
  val recomposer = Recomposer(coroutineContext)
  val composition = Composition(MapNodeApplier(style), recomposer)

  launch {
    recomposer.runRecomposeAndApplyChanges()
  }

  val mapScope = object : StyleScope {
    override val style: Style get() = style
  }

  composition.setContent {
    mapScope.content()
  }
}
