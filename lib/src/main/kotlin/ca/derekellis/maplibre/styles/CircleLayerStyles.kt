@file:SuppressLint("ComposableNaming")

package ca.derekellis.maplibre.styles

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import ca.derekellis.maplibre.layers.LayerDsl
import ca.derekellis.maplibre.layers.LayerScope
import com.mapbox.mapboxsdk.style.expressions.Expression
import com.mapbox.mapboxsdk.style.layers.CircleLayer
import com.mapbox.mapboxsdk.style.layers.Property.CIRCLE_PITCH_ALIGNMENT
import com.mapbox.mapboxsdk.style.layers.Property.CIRCLE_PITCH_SCALE
import com.mapbox.mapboxsdk.style.layers.Property.CIRCLE_TRANSLATE_ANCHOR
import com.mapbox.mapboxsdk.style.layers.Property.VISIBILITY
import com.mapbox.mapboxsdk.style.layers.PropertyFactory

private typealias CircleLayerScope = LayerScope<CircleLayer>

@Composable
@LayerDsl
public fun CircleLayerScope.circleBlur(blur: Float) {
  layer.withProperties(PropertyFactory.circleBlur(blur))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleBlur(expression: Expression) {
  layer.withProperties(PropertyFactory.circleBlur(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleColor(color: Int) {
  layer.withProperties(PropertyFactory.circleColor(color))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleColor(color: String) {
  layer.withProperties(PropertyFactory.circleColor(color))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleColor(color: Color) {
  layer.withProperties(PropertyFactory.circleColor(color.toArgb()))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleColor(expression: Expression) {
  layer.withProperties(PropertyFactory.circleColor(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleOpacity(opacity: Float) {
  layer.withProperties(PropertyFactory.circleOpacity(opacity))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleOpacity(expression: Expression) {
  layer.withProperties(PropertyFactory.circleBlur(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circlePitchAlignment(@CIRCLE_PITCH_ALIGNMENT alignment: String) {
  layer.withProperties(PropertyFactory.circlePitchAlignment(alignment))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circlePitchAlignment(expression: Expression) {
  layer.withProperties(PropertyFactory.circlePitchAlignment(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circlePitchScale(@CIRCLE_PITCH_SCALE scale: String) {
  layer.withProperties(PropertyFactory.circlePitchScale(scale))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circlePitchScale(expression: Expression) {
  layer.withProperties(PropertyFactory.circlePitchScale(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleRadius(radius: Float) {
  layer.withProperties(PropertyFactory.circleRadius(radius))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleRadius(expression: Expression) {
  layer.withProperties(PropertyFactory.circleRadius(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleSortKey(key: Float) {
  layer.withProperties(PropertyFactory.circleSortKey(key))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleSortKey(expression: Expression) {
  layer.withProperties(PropertyFactory.circleSortKey(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleStrokeColor(color: Int) {
  layer.withProperties(PropertyFactory.circleStrokeColor(color))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleStrokeColor(color: String) {
  layer.withProperties(PropertyFactory.circleStrokeColor(color))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleStrokeColor(color: Color) {
  layer.withProperties(PropertyFactory.circleStrokeColor(color.toArgb()))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleStrokeColor(expression: Expression) {
  layer.withProperties(PropertyFactory.circleStrokeColor(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleStrokeOpacity(opacity: Float) {
  layer.withProperties(PropertyFactory.circleStrokeOpacity(opacity))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleStrokeOpacity(expression: Expression) {
  layer.withProperties(PropertyFactory.circleStrokeOpacity(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleStrokeWidth(width: Float) {
  layer.withProperties(PropertyFactory.circleStrokeWidth(width))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleStrokeWidth(expression: Expression) {
  layer.withProperties(PropertyFactory.circleStrokeWidth(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleTranslate(offset: Array<Float>) {
  layer.withProperties(PropertyFactory.circleTranslate(offset))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleTranslate(expression: Expression) {
  layer.withProperties(PropertyFactory.circleTranslate(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleTranslateAnchor(@CIRCLE_TRANSLATE_ANCHOR anchor: String) {
  layer.withProperties(PropertyFactory.circleTranslateAnchor(anchor))
}

@Composable
@LayerDsl
public fun CircleLayerScope.circleTranslateAnchor(expression: Expression) {
  layer.withProperties(PropertyFactory.circleTranslateAnchor(expression))
}

@Composable
@LayerDsl
public fun CircleLayerScope.visibility(@VISIBILITY visibility: String) {
  layer.withProperties(PropertyFactory.visibility(visibility))
}
