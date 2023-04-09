package com.shop.eagleway.ui.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.provider.Contacts.Intents.UI
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.eagleway.R
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.HomeViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               viewModel: HomeViewModel = viewModel()) {

    val homeCardDataList = listOf<HomeCardData>(
        HomeCardData(name = "Sale", value = 0, iconRes = R.drawable.ic_sale),
        HomeCardData(name = "Orders", value = 0, iconRes = R.drawable.ic_bag),
        HomeCardData(name = "To Pay", value = 0, iconRes = R.drawable.ic_wallet),
        HomeCardData(name = "To Collect", value = 0, iconRes = R.drawable.ic_payments),
        HomeCardData(name = "Low Stocks", value = 0, iconRes = R.drawable.ic_low_stocks),
        HomeCardData(name = "Abandoned Cart", value = 0, iconRes = R.drawable.ic_abandoned_cart)
    )

    val manageBusinessDataList = listOf<ManageBusiness>(
        ManageBusiness(name = "Customers", iconRes = R.drawable.ic_customers),
        ManageBusiness(name = "Invoices", iconRes = R.drawable.ic_invoice),
        ManageBusiness(name = "Reports", iconRes = R.drawable.ic_reports),
        ManageBusiness(name = "Estimates", iconRes = R.drawable.ic_estimates),
        ManageBusiness(name = "Purchase Orders", iconRes = R.drawable.ic_purchase_orders),
        ManageBusiness(name = "To Pay", iconRes = R.drawable.ic_wallet),
        ManageBusiness(name = "To Collect", iconRes = R.drawable.ic_payments),
    )
    
    val growBusinessDataList = listOf<GrowBusiness>(
        GrowBusiness(name = "Collections", iconRes = R.drawable.ic_collections),
        GrowBusiness(name = "Coupons", iconRes = R.drawable.ic_coupon),
        GrowBusiness(name = "Store Banner", iconRes = R.drawable.ic_store_banner),
        GrowBusiness(name = "Wallet Settings", iconRes = R.drawable.ic_wallet_settings),
        GrowBusiness(name = "Marketing Banners", iconRes = R.drawable.ic_marketing_banner),
        GrowBusiness(name = "Greetings", iconRes = R.drawable.ic_favorite),
        GrowBusiness(name = "Business Card", iconRes = R.drawable.ic_card),
        GrowBusiness(name = "App Store", iconRes = R.drawable.ic_store)
    )

    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {

        Card {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 16.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = modifier.width(10.dp))
                Icon(imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = null)
                Spacer(modifier = modifier.width(10.dp))
                Text(text = viewModel.businessName, fontSize = 18.sp)

                Spacer(modifier = modifier.weight(1f))

                Switch(checked = false, onCheckedChange = {})
            }
        }

        Divider(modifier = modifier.width(5.dp))

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

private data class HomeCardData(
    val name: String,
    val value: Int,
    @DrawableRes val iconRes: Int
)

@Composable
private fun HomeCard(modifier: Modifier = Modifier,
                     homeCardData: HomeCardData = HomeCardData(name = "Sale", value = 0, iconRes = R.drawable.ic_sale)
) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(10)) {
        Row(modifier = modifier) {

            IconButton(
                onClick = {},
                modifier = modifier.padding(5.dp)
                    .clip(RoundedCornerShape(20))
                    .background(Color.Cyan)) {
                Icon(painter = painterResource(homeCardData.iconRes),
                    contentDescription = null,
                    modifier = modifier.size(30.dp)
                    )
            }

            Column {
                Text(text = "${homeCardData.value}", fontSize = 18.sp)
                Text(text = homeCardData.name, fontSize = 14.sp)
            }
        }
    }
}

private data class ManageBusiness(
    val name: String,
    @DrawableRes val iconRes: Int
)

/**
 * Manage Business Card
 * */
@Composable
private fun ManageBusinessCard(modifier: Modifier = Modifier,
                               manageBusiness: ManageBusiness = ManageBusiness("Customers", R.drawable.ic_customers) ) {
    Column {
        IconButton(
            onClick = {},
            modifier = modifier.padding(5.dp)
                .clip(RoundedCornerShape(20))
                .background(Color.Cyan)) {
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
    @DrawableRes val iconRes: Int
)

/**
 * Grow Business Card
 * */
@Composable
private fun GrowBusinessCard(modifier: Modifier = Modifier,
                               growBusiness: GrowBusiness = GrowBusiness("Customers", R.drawable.ic_collections) ) {
    Column {
        IconButton(
            onClick = {},
            modifier = modifier.padding(5.dp)
                .clip(RoundedCornerShape(20))
                .background(Color.Cyan)) {
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