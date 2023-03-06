package com.shop.eagleway.ui.main.orders

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun ShipmentReadyOrderScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        Text(text = "No orders in this section.")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ShipmentReadyOrderScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        ShipmentReadyOrderScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShipmentReadyOrderScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        ShipmentReadyOrderScreen()
    }
}