package com.ccb.proandroiddevreader

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ccb.proandroiddevreader.feed.FeedScreen
import com.ccb.proandroiddevreader.feed.FeedViewModel
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.ui.theme.ProAndroidDevReaderTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val feedViewModel: FeedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProAndroidDevReaderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val state by feedViewModel.state.collectAsState()

                    FeedScreen(
                        feedViewState = state,
                        modifier = Modifier.padding(16.dp),
                        onSelectedNews = ::onSelectedNews
                    )

                    LaunchedEffect(Unit) {
                        feedViewModel.updateFeed()
                    }
                }
            }
        }
    }

    private fun onSelectedNews(news: News) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.link))
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "Please install a web browser", Toast.LENGTH_SHORT).show()
            Timber.e(ex)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProAndroidDevReaderTheme {
        Greeting("Android")
    }
}