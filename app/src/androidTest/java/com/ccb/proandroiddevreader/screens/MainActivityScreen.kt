package com.ccb.proandroiddevreader.screens

import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.ccb.proandroiddevreader.extensions.LazyListItemPosition
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListItemNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListNode

class MainActivityScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider,
) : ComposeScreen<MainActivityScreen>(
    semanticsProvider = semanticsProvider,
    viewBuilderAction = { hasTestTag("MainScreen") }
) {
    class NewsListItemNode(
        semanticsNode: SemanticsNode,
        semanticsProvider: SemanticsNodeInteractionsProvider,
    ) : KLazyListItemNode<NewsListItemNode>(semanticsNode, semanticsProvider) {

        val title: KNode = child {
            useUnmergedTree = true
            hasTestTag("title")
        }
        val information: KNode = child {
            useUnmergedTree = true
            hasTestTag("information")
        }
    }

    val newsList = KLazyListNode(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("newsList") },
        itemTypeBuilder = {
            itemType(::NewsListItemNode)
        },
        positionMatcher = { position -> SemanticsMatcher.expectValue(LazyListItemPosition, position) }
    )

}
