package com.shop.eagleway.ui.main

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.*
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabItem
import com.shop.eagleway.R
import com.shop.eagleway.ui.main.invoice.EstimatesActivity
import com.shop.eagleway.ui.main.invoice.PurchaseOrderActivity
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.utility.InvoiceTabItem
import com.shop.eagleway.utility.toast
import com.shop.eagleway.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun InvoiceScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel(),) {

    val context = LocalContext.current
    var isClicked by remember { mutableStateOf(false) }

    val tabs = listOf(
        InvoiceTabItem.All,
        InvoiceTabItem.Paid,
        InvoiceTabItem.Unpaid
    )

    val pageState = rememberPagerState(tabs.indexOf(InvoiceTabItem.All))

    Column(modifier = modifier.fillMaxSize()) {

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = modifier.width(10.dp))

            Text(text = "Invoices", fontSize = 18.sp)

            Spacer(modifier = modifier.weight(1f))


            Spacer(modifier = modifier.weight(1f))

            FloatingActionButton(
                onClick = { },
                modifier = modifier.size(40.dp),
                backgroundColor = colorResource(id = R.color.light_pink)
            ) {
                Text(
                    text = (viewModel.timeData / 1000).toInt().toString(),
                    color = when ((viewModel.timeData /1000).toInt()) {
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

            IconButton(onClick = { isClicked = !isClicked }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = stringResource(R.string.More)
                )
            }
        }

        /**
         * Popup menu
         * */
        Box(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.End)) {
            DropdownMenu(expanded = isClicked, onDismissRequest = { isClicked = !isClicked }) {
                DropdownMenuItem(onClick = {
                    isClicked = !isClicked
                    EstimatesActivity.startActivity(context as Activity)
                }) {
                    Text(text = "Estimates")
                }
                DropdownMenuItem(onClick = {
                    isClicked = !isClicked
                    PurchaseOrderActivity.startActivity(context as Activity)
                }) {
                    Text(text = "Purchase Orders")
                }
            }
        }

        /**
         * Invoice Pager
         **/
        Tabs(tabs = tabs, pagerState = pageState)

        Spacer(modifier = modifier.height(10.dp))

        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
            ) {

            Chip(onClick = {} ) {
                Text(text = "All")
            }

            Chip(onClick = {}) {
                Text(text = "Today")
            }

            Chip(onClick = {} ) {
                Text(text = "Yesterday")
            }

            Chip(onClick = {} ) {
                Text(text = "1 week")
            }
        }

        Box(modifier = modifier.padding(bottom = 50.dp)) {
            TabsContent(tabs = tabs, pagerState = pageState)

            FloatingActionButton(
                onClick = {},
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.BottomEnd)
                    .padding(30.dp),
                backgroundColor = colorResource(id = R.color.light_pink)
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Tabs(tabs: List<InvoiceTabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        contentColor = Color.Blue,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }) {
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
private fun TabsContent(tabs: List<InvoiceTabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InvoiceScreenPreview() {
    EaglewayTheme {
        InvoiceScreen()
    }
}