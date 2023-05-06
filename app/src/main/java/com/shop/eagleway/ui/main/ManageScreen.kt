package com.shop.eagleway.ui.main

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shop.eagleway.R
import com.shop.eagleway.ui.main.manage.profile.SetProfileActivity
import com.shop.eagleway.ui.theme.EaglewayTheme
import com.shop.eagleway.viewmodel.HomeViewModel

@Composable
fun ManageScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(),
    activity: Activity? = null
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
                Text(text = "Manage", fontSize = 18.sp)

                Spacer(modifier = modifier.weight(1f))

                FloatingActionButton(
                    onClick = { },
                    modifier = modifier.size(40.dp),
                    backgroundColor = colorResource(id = R.color.purple_1)) {
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

            }
        }

        Column(modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 70.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            StoreDetailsCard(businessName = viewModel.businessName)

            MajorSettings(titleText = "Online Store Settings",
                descriptionText = "Manage delivery, payment, seo & store color")

            MajorSettings(titleText = "Invoice Settings",
                descriptionText = "Set invoice color, prefix & format")

            MajorSettings(titleText = "App Store",
                descriptionText = "Unlock free & premium tools")

            MajorSettings(titleText = "Store Android App",
                descriptionText = "Get store Android App & publish")

            MajorSettings(titleText = "Refer & Earn",
                descriptionText = "Refer & unlock more features & reports")

            MajorSettings(titleText = "Manage Staff",
                descriptionText = "Manage your business by adding staff members")

            MinorSettings(
                titleText = "Profile Details",
                descriptionText = "Set name, phone number, language & email") {
                SetProfileActivity.startActivity(activity)
            }

            MinorSettings(
                titleText = "QR code",
                descriptionText = "Download QR code") {
            }

            MinorSettings(
                titleText = "About us",
                descriptionText = "About Us, App Version and other information") {
            }

            OutlinedButton(onClick = onLogout, modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)) {
                Text(text = "Logout")
            }
        }
    }
}

@Composable
private fun MajorSettings(modifier: Modifier = Modifier,
                          titleText: String,
                          descriptionText: String ) {

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Icon(imageVector = Icons.Outlined.Add,
            contentDescription = null,
            modifier = modifier.padding(10.dp)
        )

        Column(horizontalAlignment = Alignment.Start, modifier = modifier.weight(1f)) {
            Text(text = titleText, fontSize = 18.sp)
            Text(text = descriptionText)
        }

        Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)
    }
}

@Composable
private fun MinorSettings(modifier: Modifier = Modifier,
                          titleText: String,
                          descriptionText: String,
                          onClick: () -> Unit
                          ) {
    Card(shape = RoundedCornerShape(20),
        modifier = modifier.padding(5.dp).clickable(onClick = onClick)) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically) {

            Column(horizontalAlignment = Alignment.Start,
                modifier = modifier.weight(1f)) {
                Text(text = titleText, fontSize = 18.sp)
                Text(text = descriptionText)
            }

            Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
private fun StoreDetailsCard(modifier: Modifier = Modifier, businessName: String = "") {
    Card(elevation = 4.dp, shape = RoundedCornerShape(10), modifier = modifier.padding(10.dp)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = "Store Details")

            Row {
                Text(text = businessName, fontSize = 18.sp)
                Spacer(modifier = modifier.weight(1f))
                StoreDetailsLogoCard()
            }

            Button(onClick = {},
                shape = RoundedCornerShape(50),
                modifier = modifier.fillMaxWidth()
            ) {
                Text(text = "Store Details")
            }

            Row {
                Spacer(modifier = modifier.width(10.dp))
                Text(text = "Current Plan: Free for ever")

                Spacer(modifier = modifier.weight(1f))

                Text(text = "Upgrade now")
                Spacer(modifier = modifier.width(10.dp))
            }
        }
    }
}

@Composable
private fun StoreDetailsLogoCard(modifier: Modifier = Modifier) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(10)) {
        Column(modifier = modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = null, modifier = modifier.size(20.dp))
            Text(text = "Add Logo", fontSize = 10.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StoreDetailsLogoCardLightThemePreview() {
    StoreDetailsLogoCard()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun StoreDetailsLogoCardDarkThemePreview() {
    StoreDetailsLogoCard()
}

@Preview(showBackground = true)
@Composable
private fun StoreDetailsCardLightThemePreview() {
    StoreDetailsCard()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun StoreDetailsCardDarkThemePreview() {
    StoreDetailsCard()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ManageScreenPreview() {
    EaglewayTheme {
        ManageScreen()
    }
}