package com.shop.eagleway.ui.main

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shop.eagleway.R
import com.shop.eagleway.data.ProductInfoWithImages
import com.shop.eagleway.ui.main.product.AddProductActivity
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.HomeViewModel
import com.shop.eagleway.viewmodel.LoadingState
import com.shop.eagleway.viewmodel.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

private const val TAG = "ProductScreen"
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductScreen(modifier: Modifier = Modifier,
                  homeViewModel: HomeViewModel = viewModel(),
                  productViewModel: ProductViewModel = viewModel(),
                  activity: Activity? = null
                  ) {

    val focusManager = LocalFocusManager.current
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = productViewModel.isRefresh)
    val productUiState by productViewModel.productUiState.collectAsState()

    var searchText by remember { mutableStateOf("") }

    if (productViewModel.isRefresh) {
        productViewModel.getProductInfo()
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = productViewModel::onRefresh,
        modifier = modifier.fillMaxSize()
    ) {
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
                            text = (homeViewModel.timeData / 1000).toInt().toString(),
                            color = when ((homeViewModel.timeData / 1000).toInt()) {
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

                    IconButton(onClick = {  }) {
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

                Column(modifier = modifier.padding(bottom = 10.dp)) {

                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text(text = "Search by name or brand") },
                        trailingIcon = {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(20),
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(id = R.color.purple_3), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent),
                    )

                    if (productUiState.product.isEmpty()) {
                        Text(
                            text = "You haven't added any product. Add product and then start sharing them or create them or create orders for customers",
                            modifier = modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn(modifier = modifier.padding(12.dp)) {
                            items(productUiState.product) { product ->
                                if (product.productInfo.productName!!.startsWith(searchText, ignoreCase = true)) {
                                    ProductCard(product = product)
                                }
                            }
                        }
                    }
                }

                FloatingActionButton(
                    onClick = { AddProductActivity.startActivity(activity) },
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
}

//@OptIn(ExperimentalPagerApi::class)
//@Composable
//private fun Tabs(tabs: List<ProductTabItem>, pagerState: PagerState) {
//    val scope = rememberCoroutineScope()
//
//    TabRow(
//        selectedTabIndex = pagerState.currentPage,
//        backgroundColor = Color.Transparent,
//        contentColor = Color.Blue,
//        indicator = { tabPositions ->
//            TabRowDefaults.Indicator(
//                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
//            )
//        }){
//        tabs.forEachIndexed { index, tab ->
//            Tab(
//                text = { Text(text = tab.title, color = Color.Blue) },
//                selected = pagerState.currentPage == index,
//                onClick = {
//                    scope.launch {
//                        pagerState.animateScrollToPage(index)
//                    }
//                },
//            )
//        }
//    }
//}

//@OptIn(ExperimentalPagerApi::class)
//@Composable
//private fun TabsContent(tabs: List<ProductTabItem>, pagerState: PagerState) {
//    HorizontalPager(state = pagerState, count = tabs.size) { page ->
//        tabs[page].screen()
//    }
//}

@Preview(showBackground = true, backgroundColor = 0L)
@Composable
private fun ProductCard(modifier: Modifier = Modifier, product: ProductInfoWithImages? = null) {
    Card(elevation = 4.dp,
         shape = RoundedCornerShape(20),
         backgroundColor = colorResource(id = R.color.purple_3),
        modifier = modifier.padding(4.dp)
        ) {

        Row(modifier = modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          if (product!!.productImages.isEmpty()) {
              Image(painter = painterResource(id = R.drawable.ic_placeholder),
                  contentDescription = null,
                  modifier = modifier.size(80.dp))
          } else {
              val productImage = File(LocalContext.current.filesDir,"productImage")
              if (productImage.exists()) {

                  val file = File(productImage, product.productImages.first().imageName.toString())

                  Card(elevation = 2.dp, shape = RoundedCornerShape(20)) {

                      Image(painter = rememberAsyncImagePainter(model = file.path, contentScale = ContentScale.Crop),
                          contentDescription = null,
                          modifier = modifier.size(80.dp),
                      contentScale = ContentScale.Crop)
                  }
              }
          }

            Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = product.productInfo.productName.toString(), style = MaterialTheme.typography.h6)

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Row {
                        Text(text = "Price: ")
                        Text(text = product.productInfo.salesPrice.toString(),)
                    }
                    
                    Spacer(modifier = modifier.weight(1f))

                    Row {
                        Text(text = "Quantity: ")
                        Text(text = product.productInfo.quantity.toString())
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProductScreenPreview() {
    EaglewayTheme {
        ProductScreen()
    }
}
