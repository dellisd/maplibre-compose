package ca.derekellis.maplibre

sealed interface Screen {
  data object Home : Screen

  data object BasicSample : Screen

  data object StylesSample : Screen

  data object NoStylesSample : Screen
}
