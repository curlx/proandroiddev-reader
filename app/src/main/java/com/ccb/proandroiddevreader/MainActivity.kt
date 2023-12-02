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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ccb.proandroiddevreader.bookmark.BookmarkScreen
import com.ccb.proandroiddevreader.bookmark.BookmarkViewModel
import com.ccb.proandroiddevreader.feed.FeedScreen
import com.ccb.proandroiddevreader.feed.FeedViewModel
import com.ccb.proandroiddevreader.feed.models.News
import com.ccb.proandroiddevreader.ui.model.Screen
import com.ccb.proandroiddevreader.ui.theme.ProAndroidDevReaderTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val feedViewModel: FeedViewModel by viewModels()
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProAndroidDevReaderTheme {
                val items = listOf(
                    Screen.Feed,
                    Screen.Bookmark,
                )
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigation(items, navController)
                    }
                ) { paddings ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Feed.route
                    ) {
                        navigation(
                            startDestination = "feed_list",
                            route = Screen.Feed.route,
                        ) {
                            composable("feed_list") {
                                val feedState by feedViewModel.state.collectAsState()
                                FeedScreen(
                                    feedViewState = feedState,
                                    modifier = Modifier
                                        .padding(paddings)
                                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                                        .testTag("MainScreen"),
                                    onSelectedNews = ::onSelectedNews,
                                    onRefresh = { feedViewModel.updateFeed() },
                                    onToggleBookmark = { news ->
                                        feedViewModel.toggleNewsBookmark(news)
                                    }
                                )

                                LaunchedEffect(Unit) {
                                    feedViewModel.requestInitialUpdate()
                                }
                            }
                        }
                        navigation(
                            startDestination = "bookmark_list",
                            route = Screen.Bookmark.route,
                        ) {
                            composable("bookmark_list") {
                                val bookmarkState by bookmarkViewModel.state.collectAsState()
                                BookmarkScreen(
                                    bookmarksViewState = bookmarkState,
                                    onSelectedNews = ::onSelectedNews,
                                    onToggleBookmark = { news ->
                                        bookmarkViewModel.toggleNewsBookmark(news)
                                    },
                                    modifier = Modifier
                                        .padding(paddings)
                                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BottomNavigation(
        items: List<Screen>,
        navController: NavHostController,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        NavigationBar {
            items.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = {
                        Text(text = stringResource(id = screen.title))
                    },
                    icon = {
                        Box {
                            Icon(
                                imageVector = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                                    screen.selectedIcon
                                } else {
                                    screen.unselectedIcon
                                },
                                contentDescription = stringResource(id = screen.title),
                            )
                        }
                    },
                )
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