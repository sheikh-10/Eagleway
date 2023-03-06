package com.shop.eagleway.ui.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
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
import androidx.core.view.ViewCompat.ScrollAxis
import com.google.accompanist.pager.*
import com.shop.eagleway.utility.ProductTabItem
import com.shop.eagleway.R
import com.shop.eagleway.ui.theme.EaglewayTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductScreen(modifier: Modifier = Modifier) {

    val tabs = listOf(
        ProductTabItem.Products,
        ProductTabItem.Inventory,
        ProductTabItem.Categories,
    )

    val pageState = rememberPagerState(tabs.indexOf(ProductTabItem.Products))


    Column(modifier = modifier.fillMaxSize()) {

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = modifier.width(10.dp))

            Text(text = "Products", fontSize = 18.sp)

            Spacer(modifier = modifier.weight(1f))

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }

        }


        /**
         * Product Pager
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
                    Text(text = "PRODUCT")
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Tabs(tabs: List<ProductTabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = Color.Blue,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }){
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
private fun TabsContent(tabs: List<ProductTabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProductScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        ProductScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ProductScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        ProductScreen()
    }
}