# maplibre-compose

[MapLibre] bindings for Compose UI.

## Setup

Add the library to your dependencies.

> **Warning**
> This library is still early in development and is not yet being published to maven central.

```kotlin
dependencies {
  implementaton("ca.derekellis.maplibre:maplibre-compose")
}
```

## Usage

To display a simple map:

```kotlin
MapLibreMap(
  modifier = Modifier.fillMaxSize(),
  style = "https://demotiles.maplibre.org/style.json"
)
```

### Adding Sources and Layers

To add a GeoJSON source to the map, add it to the `content` block of the map.
Vector layers can be added to the layers block of the `GeoJsonSource`. 
This will automatically tie the layer's source to the enclosing data source.

```kotlin
MapLibreMap {
  GeoJsonSource(id = "some-source", uri = URI.create("...")) {
    CircleLayer(id = "circle-layer")
  }
}
```

Layer properties are declared by calling composable functions within the `properties` block of the
corresponding layer. Recompositions on these functions will update the styling of the layer.

```kotlin
// Creating some animated property to animate the layer's circle radii
val infiniteTransition = rememberInfiniteTransition()
val radius by infiniteTransition.animateFloat(
  initialValue = 2f,
  targetValue = 10f,
  animationSpec = infiniteRepeatable(tween(1000), repeatMode = RepeatMode.Reverse),
)

CircleLayer(id = "circle-layer") {
  circleRadius(radius = radius)
  circleColor(color = Color.Red)
}
```

### Control the Map

The map's camera can be programmatically controlled through a `MapState` object. The current position
of the camera can also be queried from this object. Use the `rememberMapState()` function to get an
instance of this object to pass into the `MapLibreMap` composable.

```kotlin
// Initialize the MapState centered around the CN Tower
val mapState = rememberMapState(
  target = LatLng(43.6427307, -79.387097),
  zoom = 15f,
)

MapLibreMap(state = mapState)
```

The camera can be animated by calling some of the suspending methods on `MapState`.
Cancelling the coroutine will cancel/stop the animation.

```kotlin
val scope = rememberCoroutineScope()

Button(onClick = {
  scope.launch { 
    mapState.easeTo(zoom = 0f)
  }
}) {
  Text("Go Somewhere!")
}
```

[MapLibre]: https://maplibre.org/
