package com.ccb.proandroiddevreader.extensions

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import io.github.kakaocup.compose.node.core.BaseNode

fun BaseNode<*>.waitUntilDisplayed(composeTestRule: ComposeTestRule, timeoutMillis: Long = 2_000) {
    composeTestRule.waitUntil(timeoutMillis) {
        try {
            this.delegate.interaction.semanticsNodeInteraction.assertIsDisplayed()
            true
        } catch (e: AssertionError) {
            false
        }
    }
}