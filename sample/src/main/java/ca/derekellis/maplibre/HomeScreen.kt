package ca.derekellis.maplibre

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(navigator: Navigator) {
  Scaffold { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        painter = painterResource(id = R.drawable.maplibre_logo),
        contentDescription = "MapLibre Logo"
      )
      Button(onClick = { navigator.goTo(Screen.BasicSample) }) {
        Text(text = "Basic Sample")
      }
      Button(onClick = { navigator.goTo(Screen.StylesSample) }) {
        Text(text = "Styling Sample")
      }
    }
  }
}

@Preview
@Composable
private fun HomeScreenPreview() {
  HomeScreen(navigator = object : Navigator {
    override fun goTo(screen: Screen) {}
  })
}
