package com.shop.eagleway.ui.main

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.provider.Contacts.Intents.UI
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shop.eagleway.R
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val homeCardDataList = listOf<HomeCardData>(
        HomeCardData(name = "Sale", value = 0),
        HomeCardData(name = "Orders", value = 0),
        HomeCardData(name = "To Pay", value = 0),
        HomeCardData(name = "To Collect", value = 0),
        HomeCardData(name = "Low Stocks", value = 0),
        HomeCardData(name = "Abandoned Cart", value = 0)
    )

    val manageBusinessDataList = listOf<ManageBusiness>(
        ManageBusiness(name = "Customers"),
        ManageBusiness(name = "Invoices"),
        ManageBusiness(name = "Reports"),
        ManageBusiness(name = "Estimates"),
        ManageBusiness(name = "Purchase Orders"),
        ManageBusiness(name = "To Pay"),
        ManageBusiness(name = "To Collect"),
    )
    
    val growBusinessDataList = listOf<GrowBusiness>(
        GrowBusiness(name = "Collections"),
        GrowBusiness(name = "Coupons"),
        GrowBusiness(name = "Store Banner"),
        GrowBusiness(name = "Wallet Settings"),
        GrowBusiness(name = "Marketing Banners"),
        GrowBusiness(name = "Greetings"),
        GrowBusiness(name = "Business Card"),
        GrowBusiness(name = "App Store")
    )

//    Column(modifier = modifier.fillMaxSize()) {
//
//        Card {
//            Row(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 16.dp, horizontal = 10.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Spacer(modifier = modifier.width(10.dp))
//                Icon(imageVector = Icons.Outlined.Star,
//                    contentDescription = null)
//                Spacer(modifier = modifier.width(10.dp))
//                Text(text = "Dukhan", fontSize = 18.sp)
//
//                Spacer(modifier = modifier.weight(1f))
//
//                Icon(imageVector = Icons.Outlined.ThumbUp,
//                    contentDescription = null,
//                    modifier = modifier.size(20.dp)
//                )
//            }
//        }
//
//        Divider(modifier = modifier.width(5.dp))
//
//        Card {
//            Row(modifier = modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                Spacer(modifier = modifier.width(10.dp))
//                Icon(imageVector = Icons.Outlined.Star,
//                    contentDescription = null)
//                Spacer(modifier = modifier.width(10.dp))
//                Text(text = "Upgrade to paid plan to enjoy best of Eagleway",
//                    fontSize = 18.sp ,
//                    modifier = modifier.weight(1f)
//                    )
//
//                OutlinedButton(onClick = {}) {
//                    Text(text = "Upgrade")
//                }
//            }
//        }
//
//        Spacer(modifier = modifier.height(10.dp))
//
//        Card {
//            Column {
//                Row(modifier = modifier.fillMaxWidth()) {
//                    Column(modifier = modifier.weight(1f)) {
//                        Text(text = "Business summary", fontSize = 18.sp)
//                        Text(text = "Complete Business summary in a glance",
//                            fontSize = 16.sp)
//                    }
//
//                    Text(text = "Today", fontSize = 16.sp)
//
//                    Icon(imageVector = Icons.Outlined.KeyboardArrowDown,
//                        contentDescription = null)
//                }
//
//                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
//                    items(homeCardDataList) { homeCardData ->
//                        HomeCard(homeCardData = homeCardData)
//                    }
//                }
//            }
//        }
//
//        Card {
//            Column {
//                Text(text = "Manage Business", fontSize = 18.sp)
//                Text(text = "Manage your business with different shortcuts",
//                    fontSize = 16.sp)
//
//                LazyVerticalGrid(columns = GridCells.Fixed(4)) {
//                    items(manageBusinessDataList) { manageBusinessData ->
//                        ManageBusinessCard(manageBusiness = manageBusinessData)
//                    }
//                }
//            }
//        }
//
//        Card {
//            Column {
//                Text(text = "Grow Business", fontSize = 18.sp)
//                Text(text = "Grow Business with Online website, Socail Selling",
//                    fontSize = 16.sp)
//
//                LazyVerticalGrid(columns = GridCells.Fixed(4)) {
//                    items(growBusinessDataList) { growBusinessData ->
//                        GrowBusinessCard(growBusiness = growBusinessData)
//                    }
//                }
//            }
//        }
//
//
//    }

    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {

        Text(text = "Home Screen")
    }
}

private data class HomeCardData(
    val name: String,
    val value: Int
)

@Composable
private fun LogoCard(modifier: Modifier = Modifier) {
    Card(elevation = 4.dp) {
        Image(imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = modifier.size(60.dp)
            )
    }
}

@Composable
private fun HomeCard(modifier: Modifier = Modifier,
                     homeCardData: HomeCardData = HomeCardData(name = "Sale", value = 0)
) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(10)) {
        Row(modifier = modifier) {
            LogoCard()

            Column {
                Text(text = "${homeCardData.value}", fontSize = 18.sp)
                Text(text = homeCardData.name, fontSize = 18.sp)
            }
        }
    }
}

private data class ManageBusiness(
    val name: String
)

/**
 * Manage Business Card
 * */
@Composable
private fun ManageBusinessCard(modifier: Modifier = Modifier,
                               manageBusiness: ManageBusiness = ManageBusiness("Customers") ) {
    Column {
        Image(imageVector = Icons.Outlined.AccountBox, contentDescription = null)
        Text(text = manageBusiness.name, fontSize = 18.sp)
    }
}

private data class GrowBusiness(
    val name: String
)

/**
 * Grow Business Card
 * */
@Composable
private fun GrowBusinessCard(modifier: Modifier = Modifier,
                               growBusiness: GrowBusiness = GrowBusiness("Customers") ) {
    Column {
        Image(imageVector = Icons.Outlined.AccountBox, contentDescription = null)
        Text(text = growBusiness.name, fontSize = 18.sp)
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