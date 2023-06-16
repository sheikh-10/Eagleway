package com.shop.eagleway.ui.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.CountDownTimer
import android.provider.Contacts.Intents.UI
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.eagleway.R
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import java.util.*

private const val TAG = "HomeScreen"
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               viewModel: HomeViewModel = viewModel(),
                showInterstitialAd: () -> Unit = {}
               ) {

    val homeCardDataList = listOf<HomeCardData>(
        HomeCardData(name = "Sale", value = 0, iconRes = R.drawable.ic_sale, bgColor = R.color.light_red),
        HomeCardData(name = "Orders", value = 0, iconRes = R.drawable.ic_bag, bgColor = R.color.light_pink),
        HomeCardData(name = "To Pay", value = 0, iconRes = R.drawable.ic_wallet, bgColor = R.color.light_purple),
        HomeCardData(name = "To Collect", value = 0, iconRes = R.drawable.ic_payments, bgColor = R.color.light_deep_purple),
        HomeCardData(name = "Low Stocks", value = 0, iconRes = R.drawable.ic_low_stocks, bgColor = R.color.light_indigo),
        HomeCardData(name = "Abandoned Cart", value = 0, iconRes = R.drawable.ic_abandoned_cart, bgColor = R.color.light_blue)
    )

    val manageBusinessDataList = listOf<ManageBusiness>(
        ManageBusiness(name = "Customers", iconRes = R.drawable.ic_customers,  bgColor = R.color.light_deep_blue),
        ManageBusiness(name = "Invoices", iconRes = R.drawable.ic_invoice, bgColor = R.color.light_cyan),
        ManageBusiness(name = "Purchase Order", iconRes = R.drawable.ic_purchase_orders, bgColor = R.color.light_lime),
        ManageBusiness(name = "To Collect", iconRes = R.drawable.ic_payments, bgColor = R.color.light_deep_purple),
    )
    
    val growBusinessDataList = listOf<GrowBusiness>(
        GrowBusiness(name = "Collections", iconRes = R.drawable.ic_collections, bgColor = R.color.light_purple),
        GrowBusiness(name = "Coupons", iconRes = R.drawable.ic_coupon, bgColor = R.color.light_green),
        GrowBusiness(name = "Store Banner", iconRes = R.drawable.ic_store_banner, bgColor = R.color.light_cyan),
        GrowBusiness(name = "Wallet Settings", iconRes = R.drawable.ic_wallet_settings, bgColor = R.color.light_blue),
        GrowBusiness(name = "Greetings", iconRes = R.drawable.ic_favorite, bgColor = R.color.light_red),
        GrowBusiness(name = "Business Card", iconRes = R.drawable.ic_card, bgColor = R.color.light_pink),
    )

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
                Icon(imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = null)
                Spacer(modifier = modifier.width(10.dp))
                Text(text = viewModel.businessName, fontSize = 18.sp)

                Spacer(modifier = modifier.weight(1f))

                FloatingActionButton(
                    onClick = { },
                    modifier = modifier.size(40.dp),
                    backgroundColor = colorResource(id = R.color.purple_1)
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

                Switch(checked = false, onCheckedChange = {})
            }
        }

        Divider(modifier = modifier.width(5.dp))
            Column(modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {

                Card(elevation = 4.dp, shape = RoundedCornerShape(50),
                    modifier = modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    backgroundColor = colorResource(id = R.color.purple_1)
                    ) {
                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Spacer(modifier = modifier.width(10.dp))
                        Text(text = "Upgrade to paid plan to enjoy best of Eagleway",
                            style = MaterialTheme.typography.body2,
                            modifier = modifier.weight(1f),
                            color = Color.White
                            )

                        OutlinedButton(onClick = {}, shape = RoundedCornerShape(50)) {
                            Text(text = "Upgrade", color = colorResource(id = R.color.purple_1))
                        }
                    }
                }

                Spacer(modifier = modifier.height(10.dp))

                Card(elevation = 4.dp, 
                    shape = RoundedCornerShape(10), 
                    modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp), 
                    backgroundColor = Color.LightGray
                    ) {
                    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(modifier = modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                            Card(elevation = 2.dp, shape = RoundedCornerShape(50),
                                backgroundColor = Color.White) {
                                Column(modifier = modifier.padding(10.dp)) {
                                    Text(text = "Business summary", fontSize = 18.sp, style = MaterialTheme.typography.h6, color = colorResource(
                                        id = R.color.purple_1
                                    ))
                                }
                            }

                            FloatingActionButton(
                                onClick = { },
                                backgroundColor = colorResource(id = R.color.purple_1),
                                modifier = modifier.size(24.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.Info,
                                    contentDescription = null,
                                    tint = Color.White)
                            }
                            
                            Spacer(modifier = modifier.weight(1f))

                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(text = "Today", fontSize = 16.sp)

                                FloatingActionButton(onClick = {}, modifier = modifier.size(20.dp), backgroundColor = colorResource(
                                    id = R.color.purple_1
                                )) {
                                    Icon(imageVector = Icons.Outlined.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = Color.White
                                        )
                                }
                            }
                        }

                        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            HomeCard(modifier = modifier.weight(1f), homeCardData = homeCardDataList[0])
                            HomeCard(modifier = modifier.weight(1f), homeCardData = homeCardDataList[1])
                        }

                        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            HomeCard(modifier = modifier.weight(1f), homeCardData = homeCardDataList[2])
                            HomeCard(modifier = modifier.weight(1f), homeCardData = homeCardDataList[3])
                        }

                        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            HomeCard(modifier = modifier.weight(1f), homeCardData = homeCardDataList[4])
                            HomeCard(modifier = modifier.weight(1f), homeCardData = homeCardDataList[5])
                        }
                    }
                }

                Card(elevation = 4.dp,
                    shape = RoundedCornerShape(10), 
                    modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp), 
                    backgroundColor = Color.LightGray
                    ) {
                    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Card(elevation = 2.dp, shape = RoundedCornerShape(50), backgroundColor = Color.White) {
                                Column(modifier = modifier.padding(10.dp)) {
                                    Text(text = "Manage Business", fontSize = 18.sp, style = MaterialTheme.typography.h6, color = colorResource(
                                        id = R.color.purple_1
                                    ))
                                }
                            }

                            FloatingActionButton(
                                onClick = { },
                                backgroundColor = colorResource(id = R.color.purple_1),
                                modifier = modifier.size(24.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.Info,
                                    contentDescription = null,
                                    tint = Color.White)
                            }
                        }

                        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ManageBusinessCard(modifier = modifier.weight(1f), manageBusiness = manageBusinessDataList[0])
                            ManageBusinessCard(modifier = modifier.weight(1f),manageBusiness = manageBusinessDataList[1])
                        }
                        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            ManageBusinessCard(modifier = modifier.weight(1f),manageBusiness = manageBusinessDataList[2])
                            ManageBusinessCard(modifier = modifier.weight(1f),manageBusiness = manageBusinessDataList[3])
                        }
                    }
                }

                Card(elevation = 4.dp,
                    shape = RoundedCornerShape(10),
                    modifier = modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    backgroundColor = Color.LightGray
                    ) {
                    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {


                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Card(elevation = 2.dp, shape = RoundedCornerShape(50),
                                backgroundColor = Color.White) {
                                Column(modifier = modifier.padding(10.dp)) {
                                    Text(
                                        text = "Grow Business",
                                        fontSize = 18.sp,
                                        style = MaterialTheme.typography.h6,
                                        color = colorResource(id = R.color.purple_1)
                                    )
                                }
                            }

                            FloatingActionButton(
                                onClick = { },
                                backgroundColor = colorResource(id = R.color.purple_1),
                                modifier = modifier.size(24.dp)
                            ) {
                                Icon(imageVector = Icons.Outlined.Info,
                                    contentDescription = null,
                                    tint = Color.White)
                            }
                        }

                        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            GrowBusinessCard(modifier = modifier.weight(1f), growBusiness = growBusinessDataList[0])
                            GrowBusinessCard(modifier = modifier.weight(1f), growBusiness = growBusinessDataList[1])
                        }

                        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            GrowBusinessCard(modifier = modifier.weight(1f), growBusiness = growBusinessDataList[2])
                            GrowBusinessCard(modifier = modifier.weight(1f), growBusiness = growBusinessDataList[3])
                        }

                        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            GrowBusinessCard(modifier = modifier.weight(1f), growBusiness = growBusinessDataList[4])
                            GrowBusinessCard(modifier = modifier.weight(1f), growBusiness = growBusinessDataList[5])
                        }

                    }
                }

                Spacer(modifier = modifier.height(10.dp))

                Card {
                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically) {

                        Icon(imageVector = Icons.Outlined.Phone, contentDescription = null)

                        Spacer(modifier = modifier.width(20.dp))

                        Column {
                            Text(text = "Help and Support", fontSize = 14.sp, style = MaterialTheme.typography.h6)
                            Text(text = "Contact customer care to resolve queries", fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = modifier.height(10.dp))
            }
    }
}

private data class HomeCardData(
    val name: String,
    val value: Int,
    @DrawableRes val iconRes: Int,
    @ColorRes val bgColor: Int
)

@Composable
private fun HomeCard(modifier: Modifier = Modifier,
                     homeCardData: HomeCardData = HomeCardData(name = "Sale", value = 0, iconRes = R.drawable.ic_sale, bgColor = R.color.light_blue)
) {
    Card(modifier = modifier,
        elevation = 4.dp, shape = RoundedCornerShape(50), backgroundColor = colorResource(
        id = R.color.purple_3
    )) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color = colorResource(id = homeCardData.bgColor))) {

                        Icon(
                            painter = painterResource(homeCardData.iconRes),
                            contentDescription = null,
                            modifier = modifier.size(30.dp)
                        )
                    }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "${homeCardData.value}", fontSize = 18.sp, style = MaterialTheme.typography.body1)
                Text(text = homeCardData.name, fontSize = 14.sp, style = MaterialTheme.typography.body2)
            }
        }
    }
}

private data class ManageBusiness(
    val name: String,
    @DrawableRes val iconRes: Int,
    @ColorRes val bgColor: Int
)

/**
 * Manage Business Card
 * */
@Composable
private fun ManageBusinessCard(modifier: Modifier = Modifier,
                               manageBusiness: ManageBusiness = ManageBusiness("Customers", R.drawable.ic_customers, bgColor = R.color.light_lime) ) {
    Card(modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = colorResource(id = R.color.purple_3)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color = colorResource(id = manageBusiness.bgColor))) {

                Icon(
                    painter = painterResource(manageBusiness.iconRes),
                    contentDescription = null,
                    modifier = modifier.size(30.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = manageBusiness.name, style = MaterialTheme.typography.body1)
            }
        }
    }
}

private data class GrowBusiness(
    val name: String,
    @DrawableRes val iconRes: Int,
    @ColorRes val bgColor: Int
)

/**
 * Grow Business Card
 * */
@Composable
private fun GrowBusinessCard(modifier: Modifier = Modifier,
                               growBusiness: GrowBusiness = GrowBusiness("Customers", R.drawable.ic_collections, bgColor = R.color.light_blue) ) {
    Card(modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(50),
        backgroundColor = colorResource(id = R.color.purple_3)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color = colorResource(id = growBusiness.bgColor))) {

                Icon(
                    painter = painterResource(growBusiness.iconRes),
                    contentDescription = null,
                    modifier = modifier.size(30.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = growBusiness.name, style = MaterialTheme.typography.body1)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeCardLightThemePreview() {
    HomeCard()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun HomeCardDarkThemePreview() {
    HomeCard()
}

@Preview(showBackground = true)
@Composable
private fun ManageBusinessCardLightThemePreview() {
    ManageBusinessCard()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ManageBusinessCardDarkThemePreview() {
    ManageBusinessCard()
}

@Preview(showBackground = true)
@Composable
private fun GrowBusinessCardLightThemePreview() {
    GrowBusinessCard()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GrowBusinessCardDarkThemePreview() {
    GrowBusinessCard()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    EaglewayTheme {
        HomeScreen()
    }
}
