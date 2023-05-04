package com.shop.eagleway.ui.main.invoice

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
fun InvoicePaidScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        Text(text = "You haven't created any invoice/order")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InvoicePaidScreenPreview() {
    EaglewayTheme {
        InvoicePaidScreen()
    }
}
