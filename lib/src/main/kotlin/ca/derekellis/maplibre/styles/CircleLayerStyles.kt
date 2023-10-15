package ca.derekellis.maplibre.styles

import androidx.compose.runtime.Composable
import ca.derekellis.maplibre.layers.LayerScope
import ca.derekellis.maplibre.layers.LayerDsl
import com.mapbox.mapboxsdk.style.expressions.Expression
import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory

@Composable
@LayerDsl
public fun LayerScope<CircleLayer>.circleRadius(radius: Float) {
  layer.withProperties(PropertyFactory.circleRadius(radius))
}

@Composable
@LayerDsl
public fun LayerScope<CircleLayer>.circleRadius(expression: Expression) {
  layer.withProperties(PropertyFactory.circleRadius(expression))
}
