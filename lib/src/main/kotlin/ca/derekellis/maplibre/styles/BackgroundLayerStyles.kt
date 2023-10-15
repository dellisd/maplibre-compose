@file:SuppressLint("ComposableNaming")

package ca.derekellis.maplibre.styles

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ca.derekellis.maplibre.layers.LayerDsl
import ca.derekellis.maplibre.layers.LayerScope
import com.mapbox.mapboxsdk.style.expressions.Expression
import com.mapbox.mapboxsdk.style.layers.BackgroundLayer
import com.mapbox.mapboxsdk.style.layers.Property.VISIBILITY
import com.mapbox.mapboxsdk.style.layers.PropertyFactory

private typealias BackgroundLayerScope = LayerScope<BackgroundLayer>

@Composable
@LayerDsl
public fun BackgroundLayerScope.backgroundColor(color: Int) {
  layer.withProperties(PropertyFactory.backgroundColor(color))
}

@Composable
@LayerDsl
public fun BackgroundLayerScope.backgroundColor(color: Color) {
  layer.withProperties(PropertyFactory.backgroundColor(color.toArgb()))
}

@Composable
@LayerDsl
public fun BackgroundLayerScope.backgroundColor(color: String) {
  layer.withProperties(PropertyFactory.backgroundColor(color))
}

@Composable
@LayerDsl
public fun BackgroundLayerScope.backgroundColor(expression: Expression) {
  layer.withProperties(PropertyFactory.backgroundColor(expression))
}

@Composable
@LayerDsl
public fun BackgroundLayerScope.backgroundOpacity(opacity: Float) {
  layer.withProperties(PropertyFactory.backgroundOpacity(opacity))
}

@Composable
@LayerDsl
public fun BackgroundLayerScope.backgroundOpacity(expression: Expression) {
  layer.withProperties(PropertyFactory.backgroundOpacity(expression))
}

@Composable
@LayerDsl
public fun BackgroundLayerScope.backgroundPattern(name: String) {
  layer.withProperties(PropertyFactory.backgroundPattern(name))
}

@Composable
@LayerDsl
public fun BackgroundLayerScope.backgroundPattern(expression: Expression) {
  layer.withProperties(PropertyFactory.backgroundPattern(expression))
}

@Composable
@LayerDsl
public fun BackgroundLayerScope.visibility(@VISIBILITY visibility: String) {
  layer.withProperties(PropertyFactory.visibility(visibility))
}
