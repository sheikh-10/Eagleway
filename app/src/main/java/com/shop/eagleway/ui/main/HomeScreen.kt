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
        ManageBusiness(name = "Reports", iconRes = R.drawable.ic_reports, bgColor = R.color.light_teal),
        ManageBusiness(name = "Estimates", iconRes = R.drawable.ic_estimates, bgColor = R.color.light_green),
        ManageBusiness(name = "Purchase Orders", iconRes = R.drawable.ic_purchase_orders, bgColor = R.color.light_lime),
        ManageBusiness(name = "To Pay", iconRes = R.drawable.ic_wallet, bgColor = R.color.light_purple),
        ManageBusiness(name = "To Collect", iconRes = R.drawable.ic_payments, bgColor = R.color.light_deep_purple),
    )
    
    val growBusinessDataList = listOf<GrowBusiness>(
        GrowBusiness(name = "Collections", iconRes = R.drawable.ic_collections, bgColor = R.color.light_purple),
        GrowBusiness(name = "Coupons", iconRes = R.drawable.ic_coupon, bgColor = R.color.light_green),
        GrowBusiness(name = "Store Banner", iconRes = R.drawable.ic_store_banner, bgColor = R.color.light_cyan),
        GrowBusiness(name = "Wallet Settings", iconRes = R.drawable.ic_wallet_settings, bgColor = R.color.light_blue),
        GrowBusiness(name = "Banners", iconRes = R.drawable.ic_marketing_banner, bgColor = R.color.light_indigo),
        GrowBusiness(name = "Greetings", iconRes = R.drawable.ic_favorite, bgColor = R.color.light_red),
        GrowBusiness(name = "Business Card", iconRes = R.drawable.ic_card, bgColor = R.color.light_pink),
        GrowBusiness(name = "App Store", iconRes = R.drawable.ic_store, bgColor = R.color.light_orange)
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

                FloatingActionButton(onClick = { }, modifier = modifier.size(40.dp)) {
//                    Timer { showInterstitialAd() }
//                    Text(text = "40", fontSize = 16.sp)
                    Text(text = (viewModel.timeData / 1000).toInt().toString())
                }

                Spacer(modifier = modifier.width(10.dp))

                Switch(checked = false, onCheckedChange = {})
            }
        }

        Divider(modifier = modifier.width(5.dp))
            Column(modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {

                Card {
                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Spacer(modifier = modifier.width(10.dp))
                        Icon(imageVector = Icons.Outlined.Star,
                            contentDescription = null)
                        Spacer(modifier = modifier.width(10.dp))
                        Text(text = "Upgrade to paid plan to enjoy best of Eagleway",
                            fontSize = 12.sp ,
                            modifier = modifier.weight(1f)
                        )

                        OutlinedButton(onClick = {}, shape = RoundedCornerShape(20)) {
                            Text(text = "Upgrade")
                        }
                    }
                }

                Spacer(modifier = modifier.height(10.dp))

                Card {
                    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(modifier = modifier.fillMaxWidth()) {
                            Column(modifier = modifier.weight(1f)) {
                                Text(text = "Business summary", fontSize = 14.sp, style = MaterialTheme.typography.h6)
                                Text(text = "Complete Business summary in a glance", fontSize = 12.sp)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Today", fontSize = 12.sp)

                                Icon(imageVector = Icons.Outlined.KeyboardArrowDown,
                                    contentDescription = null)
                            }
                        }

                        LazyVerticalGrid(columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = modifier.height(200.dp)
                        ) {

                            items(homeCardDataList) { homeCardData ->
                                HomeCard(homeCardData = homeCardData)
                            }
                        }
                    }
                }

                Spacer(modifier = modifier.height(10.dp))

                Card {
                    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column {
                            Text(text = "Manage Business", fontSize = 14.sp, style = MaterialTheme.typography.h6)
                            Text(text = "Manage your business with different shortcuts", fontSize = 12.sp)
                        }

                        LazyVerticalGrid(columns = GridCells.Fixed(4),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = modifier.height(180.dp)) {
                            items(manageBusinessDataList) { manageBusinessData ->
                                ManageBusinessCard(manageBusiness = manageBusinessData)
                            }
                        }
                    }
                }

                Spacer(modifier = modifier.height(10.dp))

                Card {
                    Column(modifier = modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column {
                            Text(text = "Grow Business", fontSize = 14.sp, style = MaterialTheme.typography.h6)
                            Text(text = "Grow Business with Online website, Social Selling", fontSize = 12.sp)
                        }

                        LazyVerticalGrid(columns = GridCells.Fixed(4),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = modifier.height(200.dp)) {
                            items(growBusinessDataList) { growBusinessData ->
                                GrowBusinessCard(growBusiness = growBusinessData)
                            }
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
    Card(elevation = 4.dp, shape = RoundedCornerShape(10)) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

            IconButton(
                onClick = {},
                modifier = modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(20))
                    .background(color = colorResource(id = homeCardData.bgColor))) {

                        Icon(
                            painter = painterResource(homeCardData.iconRes),
                            contentDescription = null,
                            modifier = modifier.size(30.dp)
                        )
                    }

            Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "${homeCardData.value}", fontSize = 18.sp)
                Text(text = homeCardData.name, fontSize = 14.sp)
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
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = {},
            modifier = modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(20))
                .background(color = colorResource(id = manageBusiness.bgColor))) {
            Icon(painter = painterResource(manageBusiness.iconRes),
                contentDescription = null,
                modifier = modifier.size(30.dp)
            )
        }

        Text(text = manageBusiness.name, fontSize = 12.sp)
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
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = {},
            modifier = modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(20))
                .background(color = colorResource(id = growBusiness.bgColor))) {
            Icon(painter = painterResource(growBusiness.iconRes),
                contentDescription = null,
                modifier = modifier.size(30.dp)
            )
        }
        Text(text = growBusiness.name, fontSize = 12.sp)
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
private fun HomeScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        HomeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        HomeScreen()
    }
}