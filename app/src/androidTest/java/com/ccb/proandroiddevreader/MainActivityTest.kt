package com.ccb.proandroiddevreader

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.ccb.proandroiddevreader.extensions.waitUntilDisplayed
import com.ccb.proandroiddevreader.screens.MainActivityScreen
import com.ccb.proandroiddevreader.screens.MainActivityScreen.NewsListItemNode
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun showNewsFeedContent() {
        onComposeScreen<MainActivityScreen>(composeTestRule) {
            newsList {
                waitUntilDisplayed(composeTestRule)

                childAt<NewsListItemNode>(0) {
                    title.assertIsDisplayed()
                    information.assertTextContains("Published", substring = true)
                }
            }
        }
    }
}
