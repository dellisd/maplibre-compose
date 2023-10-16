package ca.derekellis.maplibre.samples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import ca.derekellis.maplibre.DemoStyle
import ca.derekellis.maplibre.MapCallback
import ca.derekellis.maplibre.MapLibreMap
import ca.derekellis.maplibre.Navigator
import ca.derekellis.maplibre.Screen
import ca.derekellis.maplibre.rememberMapState
import java.time.Duration
import java.util.Date

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MapCallbackSample(navigator: Navigator) {
  val start = remember { Date() }
  Scaffold(topBar = {
    SampleAppBar(title = "Map Callback Sample", onNavigate = { navigator.goTo(Screen.Home) })
  }) { innerPadding ->
    val eventLog = remember { mutableStateListOf<String>() }
    val mapState = rememberMapState(padding = innerPadding)

    fun addLog(text: String) {
      val diff = Duration.ofMillis(Date().time - start.time)
      eventLog.add(
        "[%d.%s] %s".format(
          diff.toSeconds(),
          diff.toMillisPart().toString().padEnd(3, '0'),
          text,
        ),
      )
      if (eventLog.size > 50) {
        eventLog.removeFirst()
      }
    }

    Column(
      modifier = Modifier.consumeWindowInsets(innerPadding),
    ) {
      MapLibreMap(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f),
        style = DemoStyle.Default.url,
        state = mapState,
        contentPadding = innerPadding,
        logoPadding = PaddingValues(4.dp),
      ) {
        MapCallback(
          onCameraIdle = { addLog("onCameraIdle") },
          onCameraMove = { addLog("onCameraMove") },
          onCameraMoveCancel = { addLog("onCameraMoveCancel") },
          onMapClick = {
            addLog("onMapClick(%.4f, %.4f)".format(it.longitude, it.latitude))
            true
          },
        )
      }

      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1.33f),
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(16.dp),
      ) {
        val lazyListState = rememberLazyListState()
        LaunchedEffect(eventLog) {
          if (eventLog.isNotEmpty()) {
            lazyListState.animateScrollToItem(eventLog.lastIndex)
          }
        }

        LazyColumn(
          state = lazyListState,
          verticalArrangement = Arrangement.spacedBy(8.dp),
          reverseLayout = true,
        ) {
          item { Spacer(modifier = Modifier.height(16.dp)) }
          items(eventLog) { log ->
            Text(
              modifier = Modifier.padding(horizontal = 16.dp),
              text = log,
              fontFamily = FontFamily.Monospace,
            )
          }
          item { Spacer(modifier = Modifier.height(16.dp)) }
        }
      }
    }
  }
}
