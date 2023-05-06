package com.shop.eagleway.ui.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat.ScrollAxis
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.*
import com.shop.eagleway.utility.ProductTabItem
import com.shop.eagleway.R
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel(),) {
    Column(modifier = modifier.fillMaxSize()) {
        Card {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = modifier.width(10.dp))
                Text(text = "Products", fontSize = 18.sp)

                Spacer(modifier = modifier.weight(1f))

                FloatingActionButton(
                    onClick = { },
                    modifier = modifier.size(40.dp),
                    backgroundColor = colorResource(id = R.color.purple_1)
                ) {

                    Text(
                        text = (viewModel.timeData / 1000).toInt().toString(),
                        color = when ((viewModel.timeData / 1000).toInt()) {
                            9 -> Color.Red
                            8 -> Color.White
                            7 -> Color.Red
                            6 -> Color.White
                            5 -> Color.Red
                            4 -> Color.White
                            3 -> Color.Red
                            2 -> Color.White
                            1 -> Color.Red
                            else -> Color.White
                        }
                    )
                }

                Spacer(modifier = modifier.width(10.dp))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
        }


        Box(modifier = modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)) {

            Column(modifier = modifier.padding(16.dp)) {

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(text = "Search by name or brand")
                    },
                    trailingIcon = {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
                    },
                    modifier = modifier.fillMaxWidth()
                )

                Text(
                    text = "You haven't added any product. Add product and then start sharing them or create them or create orders for customers",
                    modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            FloatingActionButton(
                onClick = {},
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.BottomEnd)
                    .padding(30.dp),
                backgroundColor = colorResource(id = R.color.purple_1)
            ) {
                Icon(imageVector = Icons.Outlined.Add,
                    contentDescription = "Add",
                    tint = Color.White)
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
private fun ProductScreenPreview() {
    EaglewayTheme {
        ProductScreen()
    }
}
