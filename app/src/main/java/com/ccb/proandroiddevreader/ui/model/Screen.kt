package com.ccb.proandroiddevreader.ui.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Feed
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ccb.proandroiddevreader.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    object Feed : Screen("feed", R.string.news, Icons.Filled.Feed, Icons.Outlined.Feed)
    object Bookmark : Screen("bookmark", R.string.bookmarks, Icons.Filled.Bookmarks, Icons.Outlined.Bookmarks)
    object Setting : Screen("setting", R.string.settings, Icons.Filled.Settings, Icons.Outlined.Settings)
}