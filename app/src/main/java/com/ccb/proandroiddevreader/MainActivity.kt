package com.ccb.proandroiddevreader

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ccb.proandroiddevreader.feed.FeedScreen
import com.ccb.proandroiddevreader.feed.FeedViewModel
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.ui.model.BottomNavigationItem
import com.ccb.proandroiddevreader.ui.theme.ProAndroidDevReaderTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val feedViewModel: FeedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProAndroidDevReaderTheme {
                val state by feedViewModel.state.collectAsState()
                val items = listOf(
                    BottomNavigationItem(
                        title = getString(R.string.news),
                        selectedIcon = Icons.Filled.Newspaper,
                        unselectedIcon = Icons.Outlined.Newspaper,
                    ),
                    BottomNavigationItem(
                        title = getString(R.string.bookmarks),
                        selectedIcon = Icons.Filled.Bookmarks,
                        unselectedIcon = Icons.Outlined.Bookmarks,
                    ),
                    BottomNavigationItem(
                        title = getString(R.string.settings),
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                    ),
                )
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                    },
                                    label = {
                                        Text(text = item.title)
                                    },
                                    icon = {
                                        Box {
                                            Icon(
                                                imageVector = if (selectedItemIndex == index) {
                                                    item.selectedIcon
                                                } else {
                                                    item.unselectedIcon
                                                },
                                                contentDescription = item.title,
                                            )
                                        }
                                    },
                                )
                            }
                        }
                    }
                ) {
                    FeedScreen(
                        feedViewState = state,
                        modifier = Modifier
                            .padding(it)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .testTag("MainScreen"),
                        onSelectedNews = ::onSelectedNews,
                        onRefresh = { feedViewModel.updateFeed() }
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