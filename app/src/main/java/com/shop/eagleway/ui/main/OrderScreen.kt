package com.shop.eagleway.ui.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.HorizontalScrollView
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.*
import com.shop.eagleway.R
import com.shop.eagleway.utility.OrdersTabItem
import com.shop.eagleway.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OrderScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel(),) {

    val tabs = listOf(
        OrdersTabItem.New,
        OrdersTabItem.Confirmed,
        OrdersTabItem.ShipmentReady,
        OrdersTabItem.InTransit,
        OrdersTabItem.Completed,
        OrdersTabItem.Returns,
        OrdersTabItem.Cancelled,
    )

    val pageState = rememberPagerState(tabs.indexOf(OrdersTabItem.New))

    Column(modifier = modifier.fillMaxSize()) {

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = modifier.width(10.dp))

            Text(text = "Online Orders", fontSize = 18.sp)

            Spacer(modifier = modifier.weight(1f))


            Spacer(modifier = modifier.weight(1f))

            FloatingActionButton(onClick = {}, modifier = modifier.size(40.dp)) {
                Text(text = (viewModel.timeData / 1000).toInt().toString())
            }

            Spacer(modifier = modifier.width(10.dp))

            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }

        }

        /**
         * Order Pager
         **/
        Tabs(tabs = tabs, pagerState = pageState)

        Box(modifier = modifier.padding(bottom = 50.dp)) {
            TabsContent(tabs = tabs, pagerState = pageState)

            FloatingActionButton(
                onClick = {},
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.BottomEnd)
                    .padding(30.dp)
            ) {
                Row(modifier = modifier.padding(horizontal = 20.dp),verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
                    Spacer(modifier = modifier.width(10.dp))
                    Text(text = "ORDER")
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Tabs(modifier: Modifier = Modifier, tabs: List<OrdersTabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = Color.Blue,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        edgePadding = 0.dp,
        ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = { Text(text = tab.title, color = Color.Blue) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabsContent(modifier: Modifier = Modifier, tabs: List<OrdersTabItem>, pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        count = tabs.size) { page ->

        tabs[page].screen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OrderScreenLightThemePreview() {
    OrderScreen()
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun OrderScreenDarkThemePreview() {
    OrderScreen()
}