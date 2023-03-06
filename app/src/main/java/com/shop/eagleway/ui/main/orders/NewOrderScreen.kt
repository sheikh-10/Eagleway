package com.shop.eagleway.ui.main.orders

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
fun NewOrderScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        Text(text = "No orders in this section.")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewOrderScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        NewOrderScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun NewOrderScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        NewOrderScreen()
    }
}