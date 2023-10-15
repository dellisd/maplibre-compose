package ca.derekellis.maplibre

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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(navigator: Navigator) {
  Scaffold { innerPadding ->
    Column(
      modifier = Modifier.fillMaxSize().padding(innerPadding),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Button(onClick = { navigator.goTo(Screen.BasicSample) }) {
        Text(text = "Basic Sample")
      }
      Button(onClick = { navigator.goTo(Screen.StylesSample) }) {
        Text(text = "Styling Sample")
      }
    }
  }
}
