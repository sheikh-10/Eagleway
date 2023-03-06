package com.shop.eagleway.ui.main.invoice

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shop.eagleway.ui.theme.EaglewayTheme

@Composable
fun InvoiceAllScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        Text(text = "You haven't created any invoice/order")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InvoiceAllScreenLightThemePreview() {
    EaglewayTheme(darkTheme = false) {
        InvoiceAllScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun InvoiceAllScreenDarkThemePreview() {
    EaglewayTheme(darkTheme = true) {
        InvoiceAllScreen()
    }
}