package ca.derekellis.maplibre.samples

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleAppBar(title: String, onNavigate: () -> Unit) {
  TopAppBar(
    title = { Text(title) },
    navigationIcon = {
      IconButton(onClick = onNavigate) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Close")
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
        alpha = 0.7f
      )
    )
  )
}
